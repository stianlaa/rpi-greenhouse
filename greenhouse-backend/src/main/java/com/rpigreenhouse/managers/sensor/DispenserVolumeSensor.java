package com.rpigreenhouse.managers.sensor;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static com.rpigreenhouse.GreenhouseLogger.warnLog;
import static com.rpigreenhouse.gpio.InputPin.PIN_VOLUME_SENSOR_ECHO;
import static com.rpigreenhouse.gpio.OutputPin.PIN_VOLUME_SENSOR_TRIG;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DispenserVolumeSensor implements Sensor {

    private static final Float SPEED_OF_SOUND = 343.6f;
    private static final Long TIMEOUT_NANOS = 10000L;
    private static final Integer N = 10;

    private GpioControllerSingleton gpioControllerSingleton;
    private Deque<Float> rangeMeasurements = new LinkedList<>();

    public DispenserVolumeSensor(GpioControllerSingleton gpioControllerSingleton) {
        this.gpioControllerSingleton = gpioControllerSingleton;
    }

    @Override
    public void updateStateEstimate() {
        rangeMeasurements.add(singleMeasurement());
        if (rangeMeasurements.size() > N) {
            rangeMeasurements.pop();
        }
    }

    @Override
    public Float getStateEstimate() {
        return median((Float[]) rangeMeasurements.toArray());
    }

    public Float singleMeasurement() {
        Float result = listenToEcho();
        if (result == null) {
            warnLog("Timed out");
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

    public Float listenToEcho() {
        LocalDateTime timeout = LocalDateTime.now().plusNanos(TIMEOUT_NANOS);
        LocalDateTime pulseStart = null;
        LocalDateTime pulseEnd = null;
        emit();
        while (!gpioControllerSingleton.getPinState(PIN_VOLUME_SENSOR_ECHO) &&
                LocalDateTime.now().isBefore(timeout)) {
            pulseStart = LocalDateTime.now();
        }
        while (gpioControllerSingleton.getPinState(PIN_VOLUME_SENSOR_ECHO) &&
                LocalDateTime.now().isBefore(timeout)) {
            pulseEnd = LocalDateTime.now();
        }

        if (LocalDateTime.now().isBefore(timeout)
                && pulseEnd != null
                && pulseStart != null) {
            return ((float) ChronoUnit.NANOS.between(pulseEnd, pulseStart)) * SPEED_OF_SOUND / 2;
        } else {
            return null;
        }
    }

    public Float median(Float[] values) {
        Arrays.sort(values);
        int totalElements = values.length;
        if (totalElements % 2 == 0) {
            Float sumOfMiddleElements = values[totalElements / 2] + values[totalElements / 2 - 1];
            return (sumOfMiddleElements) / 2;
        } else {
            return values[values.length / 2];
        }
    }
}
