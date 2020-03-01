package com.rpigreenhouse.storage.plant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlantType {
    BASIL_PLANT("basil plant"),
    TOMATO_PLANT("tomato plant");

    private final String name;
}
