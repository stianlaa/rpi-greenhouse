package com.rpigreenhouse.managers.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.rpigreenhouse.GreenhouseLogger.debugLog;
import static java.lang.String.format;

@Component
public class SensorManager {

    @Autowired
    private List<Sensor> sensors;

    private Map<String, ScheduledFuture<?>> sensorSchedules = new HashMap<>();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public SensorManager() {
    }

    public void startSensorScedules(Long updateInterval) {
        for (Sensor sensor : sensors) {
            String sensorName = sensor.getClass().getCanonicalName();
            sensorSchedules.put(sensorName, service.scheduleAtFixedRate(sensor::updateStateEstimate,
                    updateInterval,
                    updateInterval,
                    TimeUnit.MICROSECONDS));
            debugLog(format("Sensorschedule %s was started", sensorName));
        }
    }
}
