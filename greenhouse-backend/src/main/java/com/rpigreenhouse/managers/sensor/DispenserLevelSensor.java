package com.rpigreenhouse.managers.sensor;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.rpigreenhouse.GreenhouseLogger.*;
import static com.rpigreenhouse.gpio.InputPin.PIN_VOLUME_SENSOR_ECHO;
import static com.rpigreenhouse.gpio.OutputPin.PIN_VOLUME_SENSOR_TRIG;
import static java.lang.String.format;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DispenserLevelSensor implements Sensor {


    private static final Long REFRESH_INTERVAL = 1000_000L; // Micros

    private static final Long TIMEOUT_NANOS = 1000_0000L; // 0.01 seconds ~ 3.4m distance sound travel
    private static final Double SPEED_OF_SOUND = 343.6;
    private static final Integer N = 10;

    private GpioControllerSingleton gpioControllerSingleton;
    private Queue<Double> rangeMeasurements = new LinkedList<>();

    public DispenserLevelSensor(GpioControllerSingleton gpioControllerSingleton) {
        this.gpioControllerSingleton = gpioControllerSingleton;
    }

    @Override
    public void updateStateEstimate() {
        Optional<Double> result = singleMeasurement();
//        infoLog(format("Updating state estimate with new measurement: %s", result));
        result.ifPresent(distance -> rangeMeasurements.add(distance));
        if (rangeMeasurements.size() > N) {
            rangeMeasurements.poll();
        }
    }

    @Override
    public Double getStateEstimate() {
        if (rangeMeasurements.isEmpty())  {
            warnLog("Couldn't return stateestimate, since measurement list was empty");
            return null;
        } else {
            List<Double> temp = new ArrayList<>(rangeMeasurements);
            return median(temp);
        }
    }

    public Optional<Double> singleMeasurement() {
        Optional<Double> result = listenToEcho();
        if (!result.isPresent()) {
            debugLog("Timed out");
        }
        return result;
    }

    private void emit() {
        gpioControllerSingleton.setPin(PIN_VOLUME_SENSOR_TRIG, false);
        waitMicros(5L);
        gpioControllerSingleton.setPin(PIN_VOLUME_SENSOR_TRIG, true);
        waitMicros(10L);
        gpioControllerSingleton.setPin(PIN_VOLUME_SENSOR_TRIG, false);
    }

    private Optional<Double> listenToEcho() {
        Long pulseStart = System.nanoTime();
        Long pulseEnd = System.nanoTime();
        emit();

        while (!gpioControllerSingleton.getPinState(PIN_VOLUME_SENSOR_ECHO) && hasNotTimedOut(pulseStart)) {
            pulseStart = System.nanoTime();
        }

        while (gpioControllerSingleton.getPinState(PIN_VOLUME_SENSOR_ECHO) && hasNotTimedOut(pulseStart)) {
            pulseEnd = System.nanoTime();
        }

        if (pulseStart == null || pulseEnd == null) {
            return Optional.empty();
        }

        if (pulseEnd - pulseStart > 0 && hasNotTimedOut(pulseStart)) {
            float nanosCounted = (pulseEnd - pulseStart);
            Double secondsTravelled = (double) nanosCounted / 1000000000;
            double distance = secondsTravelled * SPEED_OF_SOUND / 2;
//            infoLog(format("Echo received, distance found to be: %s, seconds travelled was %s", distance, secondsTravelled));
            return Optional.of(distance);
        } else {
            if (!hasNotTimedOut(pulseStart)) {
                warnLog("Timed out");
            }
        }
        return Optional.empty();
    }

    private boolean hasNotTimedOut(Long start) {
        return ((System.nanoTime() - start) < TIMEOUT_NANOS);
    }

    private Double median(List<Double> values) {
        values.sort(Double::compareTo);
        int totalElements = values.size();
        if (totalElements % 2 == 0) {
            Double sumOfMiddleElements = values.get(totalElements / 2) + values.get(totalElements / 2 - 1);
            return (sumOfMiddleElements) / 2;
        } else {
            return values.get(values.size() / 2);
        }
    }

    private static void waitMicros(long micros) {
        long waitUntil = System.nanoTime() + (micros * 1_000); //
        while (waitUntil > System.nanoTime()) {
            ;
        }
    }

    public Long getRefreshInterval() {
        return REFRESH_INTERVAL;
    }
}
