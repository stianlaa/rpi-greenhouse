package com.rpigreenhouse.controller;

import com.rpigreenhouse.consumer.WeatherConsumer;
import com.rpigreenhouse.consumer.WeatherStatus;
import com.rpigreenhouse.managers.sensor.DispenserVolumeSensor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("rest/weather/")
public class WeatherController {

    private WeatherConsumer weatherConsumer;

    private DispenserVolumeSensor dispenserVolumeSensor;

    public WeatherController(WeatherConsumer weatherConsumer,
                             DispenserVolumeSensor dispenserVolumeSensor) {
        this.weatherConsumer = weatherConsumer;
        this.dispenserVolumeSensor = dispenserVolumeSensor;
    }

    @CrossOrigin
    @RequestMapping(value = "getweather", method = RequestMethod.GET, produces = "application/json")
    public WeatherStatus getCurrentWeather() {
        return WeatherStatus.builder().temperature(5.0).humidity(2.0).cloudiness(4.5).build(); // todo remove when frequent testing is complete, and setup cache system
    }

    //    public WeatherStatus getCurrentWeather() {
    //        return weatherConsumer.fetchWeatherForecast();
    //    }

    @CrossOrigin
    @RequestMapping(value = "getdistance", method = RequestMethod.GET, produces = "application/json")
    public Double getDistanceEstimate() {
        Double distance = dispenserVolumeSensor.getStateEstimate();
        return distance;
    }

}
