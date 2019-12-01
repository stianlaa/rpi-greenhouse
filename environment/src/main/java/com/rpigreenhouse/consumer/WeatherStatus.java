package com.rpigreenhouse.consumer;


import com.rpigreenhouse.storage.WeatherStatusDao;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class WeatherStatus {

    private LocalDateTime sampletime;
    private Double temperature;
    private Double cloudiness;
    private Double humidity;

    public WeatherStatus(WeatherStatusDao weatherStatusDao) {
        this.sampletime = weatherStatusDao.getSampletime();
        this.cloudiness = weatherStatusDao.getCloudiness();
        this.temperature = weatherStatusDao.getTemperature();
        this.humidity = weatherStatusDao.getHumidity();
    }
}
