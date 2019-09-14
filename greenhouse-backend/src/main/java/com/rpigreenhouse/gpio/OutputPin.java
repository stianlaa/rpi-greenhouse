package com.rpigreenhouse.gpio;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OutputPin {
    PIN_VOLUME_SENSOR_TRIG(29),
    PIN_VALVE_A_1(11),
    PIN_VALVE_A_2(13),
    PIN_VALVE_A_3(15),
    PIN_VALVE_A_4(7), // todo probably have to change
    PIN_PUMP_1(16),
    PIN_PUMP_2(18);

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
