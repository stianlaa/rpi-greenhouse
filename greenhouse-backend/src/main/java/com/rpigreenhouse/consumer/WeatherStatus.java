package com.rpigreenhouse.consumer;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class WeatherStatus {

    private LocalDateTime sampletime;
    private Double cloudiness;
    private Double temperature;
    private Double humidity;
}
