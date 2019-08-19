package com.rpigreenhouse.storage;

import com.rpigreenhouse.greenhouse.Tray;
import com.rpigreenhouse.plants.Plant;
import com.rpigreenhouse.storage.plant.PlantService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GreenhouseStorage {

    private PlantService plantService;

    public GreenhouseStorage(PlantService plantService) {
        this.plantService = plantService;
    }

    public List<Plant> getPlants() {
        return plantService.getAllPlants();
    }

    public synchronized void addPlant(Plant plant) {
        plantService.saveOrUpdatePlant(plant);
    }

    public synchronized Optional<Plant> getPlant(String plantId) {
        return getPlants().stream().filter(plant -> plant.getPlantId().equals(plantId)).findFirst();
    }

    public String getStatus() {
        StringBuilder statusString = new StringBuilder();
        getPlants().forEach(plant -> statusString.append(plant.getStatus()));
        return statusString.toString();
    }

    public List<Tray> getTraysWithPlants() {
        List<Tray> trays = new ArrayList<>();
        List<Plant> plants = getPlants();

        Set<Integer> trayIds = plants.stream()
                .map(Plant::getTrayId)
                .collect(Collectors.toSet());

        for (Integer trayId : trayIds) {
            trays.add(new Tray(trays.size() + 1,
                    plants.stream()
                            .filter(plant -> plant.getTrayId() == trayId)
                            .collect(Collectors.toList())));
        }
        return trays;
    }
}
