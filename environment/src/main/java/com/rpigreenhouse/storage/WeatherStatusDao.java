package com.rpigreenhouse.storage;


import lombok.Builder;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Value
@Builder
@Entity(name = "WEATHERSTATUS_TABLE")
public class WeatherStatusDao {

    @Id
    @GeneratedValue
    private int id;

    private LocalDateTime sampletime;
    private Double cloudiness;
    private Double temperature;
    private Double humidity;
}
