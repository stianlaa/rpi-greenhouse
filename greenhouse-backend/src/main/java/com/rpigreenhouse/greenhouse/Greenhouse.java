package com.rpigreenhouse.greenhouse;

import com.rpigreenhouse.managers.WaterManager;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Greenhouse {
    private static final LocalDateTime firstWatering = LocalDateTime.now().plusSeconds(5); // todo replace with appropriate first startuptime, e.g 07:00
    private static final Long interval = 10L; // todo replace with 24 hrs

    private GreenhouseStorage greenhouseStorage;
    private WaterManager waterManager;

    @Autowired
    public void Greenhouse(GreenhouseStorage greenhouseStorage,
                           WaterManager waterManager) {
        this.greenhouseStorage = greenhouseStorage;
        this.waterManager = waterManager;

        waterManager.startWaterCheckingSchedule(firstWatering, interval);
    }
}