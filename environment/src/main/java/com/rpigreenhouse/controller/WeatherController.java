package com.rpigreenhouse.controller;

import com.rpigreenhouse.consumer.WeatherConsumer;
import com.rpigreenhouse.consumer.WeatherStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("rest/weather/")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherConsumer weatherConsumer;

    @GetMapping(value = "getweather", produces = "application/json")
    public WeatherStatus getCurrentWeather() {
        return weatherConsumer.fetchWeatherForecast();
    }
}
