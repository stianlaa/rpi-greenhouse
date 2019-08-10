package com.rpigreenhouse.controller;

import com.rpigreenhouse.exceptions.WaterManagerBusyException;
import com.rpigreenhouse.managers.watering.WaterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.rpigreenhouse.GreenhouseLogger.errorLog;


@RestController
@RequestMapping("rest/watering/")
public class WateringController {

    private WaterManager waterManager;

    @Autowired
    public WateringController(WaterManager waterManager) {
        this.waterManager = waterManager;
    }

    @CrossOrigin
    @GetMapping("nextwatering")
    public LocalDateTime getNextWaterTime() {
        return waterManager.getNextWaterTime();
    }

    @CrossOrigin
    @GetMapping("start")
    public String startWaterSchedule() {
        return waterManager.startWaterCheckingSchedule(LocalDateTime.now().plusSeconds(5), 120L).toString();
    }

    @CrossOrigin
    @GetMapping("stop")
    public void stopWaterSchedule() {
        waterManager.stopWaterCheckingSchedule();
    }

    @CrossOrigin
    @GetMapping("watertray/{trayid}/{mlvolume}")
    public void waterTrayManually(@PathVariable Integer trayid,
                                  @PathVariable Integer mlvolume) {
        if (waterManager.getBusyStatus()) {
            errorLog("The watering manager is currently busy.");
            throw new WaterManagerBusyException();
        } else {
            waterManager.giveTrayWater(trayid, mlvolume);
        }
    }
}
