package com.rpigreenhouse.controller;

import com.rpigreenhouse.consumer.WeatherConsumer;
import com.rpigreenhouse.consumer.WeatherStatus;
import com.rpigreenhouse.managers.sensor.DispenserLevelSensor;
import com.rpigreenhouse.managers.watering.WaterManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("rest/weather/")
public class WeatherController {

    private WeatherConsumer weatherConsumer;
    private DispenserLevelSensor dispenserLevelSensor;
    private WaterManager waterManager;

    public WeatherController(WeatherConsumer weatherConsumer,
                             DispenserLevelSensor dispenserLevelSensor,
                             WaterManager waterManager
    ) {
        this.weatherConsumer = weatherConsumer;
        this.dispenserLevelSensor = dispenserLevelSensor;
        this.waterManager = waterManager;
    }

    @CrossOrigin
    @GetMapping(value = "getweather", produces = "application/json")
    public WeatherStatus getCurrentWeather() {
        return WeatherStatus.builder().temperature(5.0).humidity(2.0).cloudiness(4.5).build(); // todo remove when frequent testing is complete, and setup cache system
    }

    //    public WeatherStatus getCurrentWeather() {
    //        return weatherConsumer.fetchWeatherForecast();
    //    }

    @CrossOrigin
    @GetMapping(value = "getdistance", produces = "application/json")
    public Double getDistanceEstimate() {
        return dispenserLevelSensor.getStateEstimate();
    }

    @CrossOrigin
    @GetMapping(value = "singlemeasurement", produces = "application/json")
    public Double getSingleMeasurement() {
        dispenserLevelSensor.updateStateEstimate();
        return dispenserLevelSensor.getStateEstimate();
    }

    @CrossOrigin
    @GetMapping(value = "filldispenser")
    public void fillDispenserToVolume() {

        // Expected behaviour here is for the fillToTargetVolume() method to fill up to target volume,
        // and then loop at the dispenseVolumeToTray() method

        waterManager.giveTrayWater(1, 150);
    }


}
