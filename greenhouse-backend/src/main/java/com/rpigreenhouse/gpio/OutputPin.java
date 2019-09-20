package com.rpigreenhouse.gpio;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OutputPin {
    PIN_VOLUME_SENSOR_TRIG(26), // physical pin: 32
    PIN_VALVE_A_1(0), // physical pin: 11
    PIN_VALVE_A_2(2), // physical pin: 13
    PIN_VALVE_A_3(3), // physical pin: 15
    PIN_VALVE_A_4(7), // todo physical pin: 7, probably have to change
    PIN_PUMP_1(4), // physical pin: 16
    PIN_PUMP_2(5); // physical pin: 18

    private Integer pinAddress;

    OutputPin(Integer pinAddress) {
        this.pinAddress = pinAddress;
    }

    public Integer addr() {
        return pinAddress;
    }

    public static List<Integer> getAllOutPins() {
        return Arrays.stream(OutputPin.values()).map(OutputPin::addr).collect(Collectors.toList());
    }
}
