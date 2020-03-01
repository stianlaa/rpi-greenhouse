package com.rpigreenhouse.managers.sensor.dispenserLevel;

import com.rpigreenhouse.gpio.GpioController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("local")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class DispenserLevelSensorLocal implements DispenserLevelSensor {

    private Random random = new Random();
    private static final Long REFRESH_INTERVAL = 1000_000L; // Micros

    private final GpioController gpioController;

    @Override
    public void updateStateEstimate() {}

    @Override
    public Double getStateEstimate() {
            return 0.2 + 0.01*random.nextDouble();
    }

    @Override
    public Long getRefreshInterval() {
        return REFRESH_INTERVAL;
    }
}
