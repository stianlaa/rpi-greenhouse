package com.rpigreenhouse.controller;

import com.rpigreenhouse.consumer.WeatherConsumer;
import com.rpigreenhouse.managers.watering.WaterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("rest/")
public class WateringController {

    private WaterManager waterManager;
    private WeatherConsumer weatherConsumer;


    private Boolean testToggle;
    private static final int TESTPIN_1 = 0;
    private static final int TESTPIN_2 = 2;

    @Autowired
    public WateringController(WaterManager waterManager,
                              WeatherConsumer weatherConsumer) {
        this.waterManager = waterManager;
        this.weatherConsumer = weatherConsumer;
        this.testToggle = false;
    }

    @CrossOrigin
    @GetMapping("nextwatering")
    public LocalDateTime getNextWaterTime() {
        return waterManager.getNextWaterTime();
    }

    @CrossOrigin
    @GetMapping("startwatersystem") // todo remove, used for test
    public String startWaterSystem() {
        waterManager.startWaterCheckingSchedule(LocalDateTime.now().plusSeconds(5), 120L);
        return waterManager.getNextWaterTime().toString();
    }

}
