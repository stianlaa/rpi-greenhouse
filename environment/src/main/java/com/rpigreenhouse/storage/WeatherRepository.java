package com.rpigreenhouse.storage;

import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<WeatherStatusDao, Integer> {
}
