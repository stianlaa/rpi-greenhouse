package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValveRegulator {

    // key: trayId, value: wiringpi Pin
    private static final Map<Integer, Integer> TRAY_PIN_MAP = new HashMap<Integer, Integer>() {
        {
            put(1, 1);
            put(2, 2);
            put(3, 3);
            put(4, 4);
        }
    };

    private GpioControllerSingleton gpioControllerSingleton;

    public ValveRegulator(GpioControllerSingleton gpioControllerSingleton) {
        this.gpioControllerSingleton = gpioControllerSingleton;
    }

    public void selectTray(Integer trayId) {
        // close all other valve exit than that corresponding to trayId
        for (Integer pinAddress : TRAY_PIN_MAP.keySet()) {
            if (pinAddress.equals(TRAY_PIN_MAP.get(trayId))) {
                gpioControllerSingleton.setPin(pinAddress, true);
            } else {
                gpioControllerSingleton.setPin(pinAddress, false);
            }
        }
    }
}
