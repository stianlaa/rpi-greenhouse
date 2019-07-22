package com.rpigreenhouse.storage.weather;

import com.rpigreenhouse.consumer.WeatherStatus;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

//    public List<WeatherStatus> getWeatherFromTo(LocalDateTime from, LocalDateTime to) {
//        List<Person> persons = new ArrayList<Person>();
//        personRepository.findAll().forEach(person -> persons.add(person));
//        return persons;
//    }

    public WeatherStatus getMostRecentWeatherStatus(Integer id) {
        return weatherRepository.findById(id).get();
    }

    public void saveOrUpdateWeatherStatus(WeatherStatus weatherStatus) {
        weatherRepository.save(weatherStatus);
    }

    public void delete(Integer id) {
        weatherRepository.deleteById(id);
    }
}
