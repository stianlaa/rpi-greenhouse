package com.rpigreenhouse.greenhouse;

import com.rpigreenhouse.plants.Plant;

import java.util.List;

public class Tray {
    private List<Plant> containsPlants;

    public Tray(List<Plant> containsPlants) {
        this.containsPlants = containsPlants;
    }

    public void addPlant(Plant plant) {
        containsPlants.add(plant);
    }

    public List<Plant> getPlants() {
        return containsPlants;
    }
}