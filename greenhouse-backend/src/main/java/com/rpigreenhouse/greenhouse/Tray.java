package com.rpigreenhouse.greenhouse;

import com.rpigreenhouse.plants.Plant;

import java.util.List;

public class Tray {
    private Integer trayId;
    private List<Plant> containsPlants;

    public Tray(Integer trayId, List<Plant> containsPlants) {
        this.containsPlants = containsPlants;
        this.trayId = trayId;
    }

    public Integer getTrayId() {
        return trayId;
    }

    public List<Plant> getPlants() {
        return containsPlants;
    }
}