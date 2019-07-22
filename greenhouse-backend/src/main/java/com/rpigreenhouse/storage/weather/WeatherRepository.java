package com.rpigreenhouse.storage.weather;

import com.rpigreenhouse.consumer.WeatherStatus;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<WeatherStatus, Integer> {
}
