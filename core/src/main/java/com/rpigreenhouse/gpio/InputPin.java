package com.rpigreenhouse.gpio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum InputPin {
    PIN_VOLUME_SENSOR_ECHO(22); // physical pin: 31

    private final Integer pinAddress;

    public static List<Integer> getAllInPins() {
        return Arrays.stream(InputPin.values()).map(InputPin::getPinAddress).collect(Collectors.toList());
    }

}
