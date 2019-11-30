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

import static com.rpigreenhouse.GreenhouseLogger.infoLog;
import static java.lang.String.format;

@Component
public class SensorManager {

    @Autowired
    private List<Sensor> sensors;

    private Map<String, ScheduledFuture<?>> sensorSchedules = new HashMap<>();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public void startSensorScedules() {
        for (Sensor sensor : sensors) {
            String sensorName = sensor.getClass().getCanonicalName();
            sensorSchedules.put(sensorName, service.scheduleAtFixedRate(sensor::updateStateEstimate,
                    sensor.getRefreshInterval(),
                    sensor.getRefreshInterval(),
                    TimeUnit.MICROSECONDS));
            infoLog(format("Sensorschedule %s was started", sensorName));
        }
    }
}
