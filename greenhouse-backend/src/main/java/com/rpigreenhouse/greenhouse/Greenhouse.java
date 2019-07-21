package com.rpigreenhouse.greenhouse;

import com.rpigreenhouse.consumer.WeatherStatus;
import com.rpigreenhouse.managers.WaterManager;
import com.rpigreenhouse.storage.GreenhouseStorage;
import com.rpigreenhouse.storage.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Greenhouse {
    private static final LocalDateTime firstWatering = LocalDateTime.now().plusSeconds(5); // todo replace with appropriate first startuptime, e.g 07:00
    private static final Long interval = 10L; // todo replace with 24 hrs

    private GreenhouseStorage greenhouseStorage;
    private WaterManager waterManager;
    private WeatherService weatherService;

    @Autowired
    public void Greenhouse(GreenhouseStorage greenhouseStorage,
                           WaterManager waterManager,
                           WeatherService weatherService) {
        this.greenhouseStorage = greenhouseStorage;
        this.waterManager = waterManager;
        this.weatherService = weatherService;

        weatherService.saveOrUpdateWeatherStatus(WeatherStatus.builder() // todo remove after db in place.
                .temperature(50.0)
                .build());
        weatherService.saveOrUpdateWeatherStatus(WeatherStatus.builder()
                .temperature(75.0)
                .build());

        //        waterManager.startWaterCheckingSchedule(firstWatering, interval); // todo resume when relevant.
    }
}