package com.rpigreenhouse.gpio;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum InputPin {
    PIN_VOLUME_SENSOR_ECHO(31);

    private Integer pinAddress;

    InputPin(Integer pinAddress) {
        this.pinAddress = pinAddress;
    }

    public Integer addr() {
        return pinAddress;
    }

    public static List<Integer> getAllInPins() {
        return Arrays.stream(InputPin.values()).map(InputPin::addr).collect(Collectors.toList());
    }

}
