package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import com.rpigreenhouse.gpio.OutputPin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.rpigreenhouse.gpio.OutputPin.PIN_PUMP_1;
import static com.rpigreenhouse.gpio.OutputPin.PIN_PUMP_2;

@Component
@RequiredArgsConstructor
public class PumpRegulator {
    private static final List<OutputPin> PUMP_PIN_ADDRESS_LIST = Arrays.asList(PIN_PUMP_1, PIN_PUMP_2);
    private final GpioControllerSingleton gpioControllerSingleton;

    public void setPumpMode(Boolean pumpState) {
        PUMP_PIN_ADDRESS_LIST.forEach(pin -> gpioControllerSingleton.setPin(pin, pumpState));
    }
}
