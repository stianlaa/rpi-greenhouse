package com.rpigreenhouse.controller;

import com.rpigreenhouse.exceptions.WaterManagerBusyException;
import com.rpigreenhouse.managers.watering.PumpRegulator;
import com.rpigreenhouse.managers.watering.WaterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.rpigreenhouse.GreenhouseLogger.errorLog;


@RestController
@RequestMapping("rest/watering/")
@RequiredArgsConstructor
public class WateringController {

    private final WaterManager waterManager;
    private final PumpRegulator pumpRegulator;

    @GetMapping("nextwatering")
    public LocalDateTime getNextWaterTime() {
        return waterManager.getNextWaterTime();
    }

    @GetMapping("start")
    public String startWaterSchedule() {
        return waterManager.startWaterCheckingSchedule(LocalDateTime.now().plusSeconds(5), 120L).toString();
    }

    @GetMapping("stop")
    public void stopWaterSchedule() {
        waterManager.stopWaterCheckingSchedule();
    }

    @GetMapping("watertray/{trayid}/{mlvolume}")
    public void waterTrayManually(@PathVariable Integer trayid,
                                  @PathVariable Integer mlvolume) {
        if (waterManager.getIsBusy()) {
            errorLog("The watering manager is currently busy.");
            throw new WaterManagerBusyException();
        } else {
            waterManager.giveTrayWater(trayid, mlvolume);
        }
    }

}
