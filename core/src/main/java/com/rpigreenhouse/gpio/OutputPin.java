package com.rpigreenhouse.gpio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum OutputPin {
    PIN_VOLUME_SENSOR_TRIG(26), // physical pin: 32
    PIN_VALVE_A_1(0), // physical pin: 11
    PIN_VALVE_A_2(2), // physical pin: 13
    PIN_VALVE_A_3(3), // physical pin: 15
    PIN_VALVE_A_4(7), // physical pin: 7
    PIN_PUMP_1(4), // physical pin: 16
    PIN_PUMP_2(5); // physical pin: 18

    private final Integer pinAddress;

    public static List<Integer> getAllOutPins() {
        return Arrays.stream(OutputPin.values()).map(OutputPin::getPinAddress).collect(Collectors.toList());
    }
}
