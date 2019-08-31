package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PumpRegulator {
    // Responsible for gathering and offering control of the pumps

    private static final List<Integer> PUMP_PIN_ADDRESS_LIST = Arrays.asList(0, 1);
    private GpioControllerSingleton gpioControllerSingleton;

    public PumpRegulator(GpioControllerSingleton gpioControllerSingleton) {
        this.gpioControllerSingleton = gpioControllerSingleton;
    }

    public void setPumpMode(Boolean pumpState) {
        PUMP_PIN_ADDRESS_LIST.forEach(pinAddress -> gpioControllerSingleton.setPin(pinAddress, pumpState));
    }
}
