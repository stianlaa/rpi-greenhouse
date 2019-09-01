package com.rpigreenhouse.gpio;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OutputPin {
    PIN_VOLUME_SENSOR_TRIG(1),
    PIN_VALVE_A_1(2),
    PIN_VALVE_A_2(3),
    PIN_VALVE_A_3(4),
    PIN_VALVE_A_4(5),
    PIN_PUMP_1(6),
    PIN_PUMP_2(7);

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
