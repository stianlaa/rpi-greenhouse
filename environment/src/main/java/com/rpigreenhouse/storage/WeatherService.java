package com.rpigreenhouse.storage;

import com.rpigreenhouse.consumer.WeatherStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    public final WeatherRepository weatherRepository;

    public WeatherStatus getMostRecentWeatherStatus(Integer id) {
        return new WeatherStatus(weatherRepository.findById(id).get());
    }

    public void saveOrUpdateWeatherStatus(WeatherStatus weatherStatus) {
        weatherRepository.save(
                WeatherStatusDao.builder()
                        .temperature(weatherStatus.getTemperature())
                        .humidity(weatherStatus.getHumidity())
                        .cloudiness(weatherStatus.getCloudiness())
                        .build()
        );
    }

    public void delete(Integer id) {
        weatherRepository.deleteById(id);
    }
}
