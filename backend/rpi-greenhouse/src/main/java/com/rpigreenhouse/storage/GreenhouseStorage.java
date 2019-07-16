package com.rpigreenhouse.storage;

import com.rpigreenhouse.greenhouse.Tray;
import com.rpigreenhouse.plants.Plant;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// supposed to
public class GreenhouseStorage {
    // todo make thread safe
    private Map<Integer, Tray> trays;

    public GreenhouseStorage() {
        this.trays = new HashMap<>(); // fetch from storage.
    }

    public void addPlant(Integer trayId, Plant plant) {
        trays.get(trayId).addPlant(plant);
    }

    public void addTray(List<Plant> plants) {
        trays.put(trays.size() + 1, new Tray(plants));
    }

    public List<Plant> getPlants() {
        return trays.values().stream()
                .flatMap(tray -> tray.getPlants().stream())
                .collect(Collectors.toList());
    }

    public String getStatus() {
        StringBuilder statusString = new StringBuilder();
        getPlants().forEach(plant -> statusString.append(plant.getStatus()));
        return statusString.toString();
    }

    public Optional<Plant> getPlant(String plantId) {
        return getPlants().stream().filter(plant -> plant.getPlantId().equals(plantId)).findFirst();
    }
}
