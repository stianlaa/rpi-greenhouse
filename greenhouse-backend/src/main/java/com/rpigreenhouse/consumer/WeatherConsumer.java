package com.rpigreenhouse.consumer;


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
public class WeatherConsumer {
    private static final String YR_BASE_URL = "https://api.met.no/weatherapi/locationforecast/1.9/";
    //params = {'lat': '59.91', 'lon': '10.63'}
    private RestTemplate restTemplate;

    public WeatherConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherStatus fetchWeatherForecast() {
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(YR_BASE_URL).queryParam("lat", "59.91").queryParam("lon", "10.63").build();

        String testThisYo = restTemplate.getForObject(uri.toString(), String.class);
        return parseAndExtractRelevantFields(testThisYo);
    }

    private WeatherStatus parseAndExtractRelevantFields(String weatherStatusXml) {
        Document doc = Jsoup.parse(weatherStatusXml, "", Parser.xmlParser());

        // todo make readable
        Optional<Element> weatherOptional = doc
                .select("product")
                .select("time")
                .stream()
                .filter(element -> element.getElementsByAttribute("from").toString().contains(LocalDateTime.now().toString().substring(0, 13)))
                .findFirst();

        if (weatherOptional.isPresent()) { // todo make robust, perhaps only accept full WeatherStatus objects
            return WeatherStatus.builder()
                    .sampletime(LocalDateTime.parse(weatherOptional.get().attr("from").substring(0, 19)))
                    .temperature(Double.parseDouble(weatherOptional.get().select("temperature").first().attr("value")))
                    .cloudiness(Double.parseDouble(weatherOptional.get().select("cloudiness").first().attr("percent")))
                    .humidity(Double.parseDouble(weatherOptional.get().select("humidity").first().attr("value")))
                    .build();
        }
        return null;
    }
}
