package com.rpigreenhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Tray {
    private Integer trayId;
    private List<Plant> plants;
}