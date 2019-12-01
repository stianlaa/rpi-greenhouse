package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import com.rpigreenhouse.gpio.OutputPin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.rpigreenhouse.GreenhouseLogger.infoLog;
import static com.rpigreenhouse.gpio.OutputPin.*;
import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class ValveRegulator {

    private static Map<Integer, OutputPin> TRAY_PIN_MAP = new HashMap<>();

    static {
        TRAY_PIN_MAP.put(1, PIN_VALVE_A_1);
        TRAY_PIN_MAP.put(2, PIN_VALVE_A_2);
        TRAY_PIN_MAP.put(3, PIN_VALVE_A_3);
        TRAY_PIN_MAP.put(4, PIN_VALVE_A_4);
    }

    private final GpioControllerSingleton gpioControllerSingleton;

    public void directValveToTray(Integer trayId) {
        // close all other valve exits than that corresponding to trayId
        infoLog(format("Dispensing to tray: %s, with pin address %s", trayId, TRAY_PIN_MAP.get(trayId)));
        for (Integer trayKey : TRAY_PIN_MAP.keySet()) {
            if (trayKey.equals(trayId)) {
                gpioControllerSingleton.setPin(TRAY_PIN_MAP.get(trayKey), true);
            } else {
                infoLog(format("Not dispensing to tray: %s, with pin address %s", trayKey, TRAY_PIN_MAP.get(trayKey)));
                gpioControllerSingleton.setPin(TRAY_PIN_MAP.get(trayKey), false);
            }
        }
    }

    public void closeAllValves() {
        for (Integer trayKey : TRAY_PIN_MAP.keySet()) {
            gpioControllerSingleton.setPin(TRAY_PIN_MAP.get(trayKey), false);
        }
    }
}
