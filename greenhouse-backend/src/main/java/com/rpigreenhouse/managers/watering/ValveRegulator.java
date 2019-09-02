package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import com.rpigreenhouse.gpio.OutputPin;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.rpigreenhouse.gpio.OutputPin.*;

@Component
public class ValveRegulator {

    private static final Map<Integer, OutputPin> TRAY_PIN_MAP = new HashMap<Integer, OutputPin>() {
        {
            put(1, PIN_VALVE_A_1);
            put(2, PIN_VALVE_A_2);
            put(3, PIN_VALVE_A_3);
            put(4, PIN_VALVE_A_4);
        }
    };

    private GpioControllerSingleton gpioControllerSingleton;

    public ValveRegulator(GpioControllerSingleton gpioControllerSingleton) {
        this.gpioControllerSingleton = gpioControllerSingleton;
    }

    public void directValveToTray(Integer trayId) {
        // close all other valve exits than that corresponding to trayId
        for (Integer trayKey : TRAY_PIN_MAP.keySet()) {
            if (trayKey.equals(trayId)) {
                gpioControllerSingleton.setPin(TRAY_PIN_MAP.get(trayId), true);
            } else {
                gpioControllerSingleton.setPin(TRAY_PIN_MAP.get(trayId), false);
            }
        }
    }

    public void closeAllValves() {
        for (Integer trayKey : TRAY_PIN_MAP.keySet()) {
            gpioControllerSingleton.setPin(TRAY_PIN_MAP.get(trayKey), true);
        }
    }
}
