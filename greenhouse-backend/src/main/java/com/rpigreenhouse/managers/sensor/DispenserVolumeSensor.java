package com.rpigreenhouse.managers.sensor;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static com.rpigreenhouse.GreenhouseLogger.debugLog;
import static com.rpigreenhouse.gpio.InputPin.PIN_VOLUME_SENSOR_ECHO;
import static com.rpigreenhouse.gpio.OutputPin.PIN_VOLUME_SENSOR_TRIG;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DispenserVolumeSensor implements Sensor {

    private static final Double SPEED_OF_SOUND = 343.6;
    private static final Long TIMEOUT_NANOS = 100000L;
    private static final Integer N = 10;

    private GpioControllerSingleton gpioControllerSingleton;
    private Queue<Double> rangeMeasurements = new LinkedList<>();

    public DispenserVolumeSensor(GpioControllerSingleton gpioControllerSingleton) {
        this.gpioControllerSingleton = gpioControllerSingleton;
    }

    @Override
    public void updateStateEstimate() {
        rangeMeasurements.add(singleMeasurement());
        if (rangeMeasurements.size() > N) {
            rangeMeasurements.poll();
        }
    }

    @Override
    public Double getStateEstimate() {
        List<Double> temp = new ArrayList<>();
        for (Double measurement : rangeMeasurements) {
            System.out.println(measurement);
            temp.add(measurement);
        }
        return median(temp);
    }

    public Double singleMeasurement() {
        Double result = listenToEcho();
        if (result == null) {
            debugLog("Timed out");
        }
        return result;
    }

    public void emit() {
        try {
            gpioControllerSingleton.setPin(PIN_VOLUME_SENSOR_TRIG, false);
            TimeUnit.MICROSECONDS.sleep(5L);
            gpioControllerSingleton.setPin(PIN_VOLUME_SENSOR_TRIG, true);
            TimeUnit.MICROSECONDS.sleep(10L);
            gpioControllerSingleton.setPin(PIN_VOLUME_SENSOR_TRIG, false);
            TimeUnit.MICROSECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Double listenToEcho() {
        LocalDateTime pulseStart = null;
        LocalDateTime pulseEnd = null;
        emit();
        LocalDateTime timeout = LocalDateTime.now().plusNanos(TIMEOUT_NANOS);
        do {
            pulseStart = LocalDateTime.now();
        } while (!gpioControllerSingleton.getPinState(PIN_VOLUME_SENSOR_ECHO) &&
                LocalDateTime.now().isBefore(timeout));

        do {
            pulseEnd = LocalDateTime.now();
        } while (gpioControllerSingleton.getPinState(PIN_VOLUME_SENSOR_ECHO) &&
                LocalDateTime.now().isBefore(timeout));

        if (LocalDateTime.now().isBefore(timeout)) {
            return ((double) ChronoUnit.NANOS.between(pulseEnd, pulseStart)) * SPEED_OF_SOUND / 2;
        } else {
            return null;
        }
    }

    public Double median(List<Double> values) {
        values.sort(Double::compareTo);
        int totalElements = values.size();
        if (totalElements % 2 == 0) {
            Double sumOfMiddleElements = values.get(totalElements / 2) + values.get(totalElements / 2 - 1);
            return (sumOfMiddleElements) / 2;
        } else {
            return values.get(values.size() / 2);
        }
    }
}
