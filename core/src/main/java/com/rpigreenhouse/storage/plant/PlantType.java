package com.rpigreenhouse.storage.plant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlantType {
    BASIL_PLANT("basil plant"),
    TOMATO_PLANT("tomato plant");

    private String name;
}
