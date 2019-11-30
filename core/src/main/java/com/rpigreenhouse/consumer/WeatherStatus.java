package com.rpigreenhouse.consumer;

import lombok.Builder;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Value
@Builder
@Entity(name = "WEATHERSTATUS_TABLE")
public class WeatherStatus { // todo consider adding DAO layer

    @Id
    @GeneratedValue
    private int id;

    private LocalDateTime sampletime;
    private Double cloudiness;
    private Double temperature;
    private Double humidity;
}
