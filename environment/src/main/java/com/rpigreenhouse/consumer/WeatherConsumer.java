package com.rpigreenhouse.consumer;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class WeatherConsumer {

    private static final String YR_BASE_URL = "https://api.met.no/weatherapi/locationforecast/1.9/";

    private final RestTemplate restTemplate;
    private WeatherStatus previousWeatherStatus;

    public WeatherStatus fetchWeatherForecast() {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(YR_BASE_URL)
                .queryParam("lat", "59.91")
                .queryParam("lon", "10.63").build();

        if (previousWeatherStatus != null && LocalDateTime.now().isBefore(previousWeatherStatus.getSampletime().plusHours(1))) {
            return previousWeatherStatus;
        } else {
            return parseAndExtractRelevantFields(restTemplate.getForObject(uri.toString(), String.class));
        }
    }

    private WeatherStatus parseAndExtractRelevantFields(String weatherStatusXml) {
        Document doc = Jsoup.parse(weatherStatusXml, "", Parser.xmlParser());

        Optional<Element> weatherOptional = doc
                .select("product").select("time")
                .stream()
                .filter(this::checkIfCurrentHour)
                .findFirst();

        WeatherStatus extractedResponse = weatherOptional.map(element ->
                new WeatherStatus(
                        LocalDateTime.parse(element.attr("from").substring(0, 19)),
                        Double.parseDouble(element.select("temperature").first().attr("value")),
                        Double.parseDouble(element.select("cloudiness").first().attr("percent")),
                        Double.parseDouble(element.select("humidity").first().attr("value")))
        ).orElse(null);
        previousWeatherStatus = extractedResponse;
        return extractedResponse;
    }

    private Boolean checkIfCurrentHour(Element timeElement) {
        return timeElement.getElementsByAttribute("from").toString()
                .contains(LocalDateTime.now().toString().substring(0, 13));
    }
}
