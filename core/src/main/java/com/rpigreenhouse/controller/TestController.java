package com.rpigreenhouse.controller;

import com.rpigreenhouse.managers.sensor.dispenserLevel.DispenserLevelSensor;
import com.rpigreenhouse.managers.watering.PumpRegulator;
import com.rpigreenhouse.managers.watering.WaterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/test/")
@RequiredArgsConstructor
public class TestController {

    public Boolean pumpmode = false;
    private final DispenserLevelSensor dispenserLevelSensor;
    private final WaterManager waterManager;
    private final PumpRegulator pumpRegulator;

    @GetMapping(value = "getdistance", produces = "application/json")
    public Double getDistanceEstimate() {
        return dispenserLevelSensor.getStateEstimate();
    }

    @GetMapping(value = "singlemeasurement", produces = "application/json")
    public Double getSingleMeasurement() {
        dispenserLevelSensor.updateStateEstimate();
        return dispenserLevelSensor.getStateEstimate();
    }

    @GetMapping(value = "testdispenser")
    public void fillDispenserToVolume() {
        waterManager.giveTrayWater(1, 150);
    }

    @GetMapping("togglepump")
    public void togglePumps() {
        pumpRegulator.setPumpMode(pumpmode);
        this.pumpmode = !pumpmode;
    }
}
