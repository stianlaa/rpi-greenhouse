package com.rpigreenhouse.service;

import com.rpigreenhouse.dto.Plant;
import com.rpigreenhouse.dto.Tray;
import com.rpigreenhouse.exceptions.functional.PlantNotFoundException;
import com.rpigreenhouse.exceptions.functional.TrayNotFoundException;
import com.rpigreenhouse.storage.plant.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.rpigreenhouse.util.PlantMapper.mapDaoToPlant;
import static com.rpigreenhouse.util.PlantMapper.mapPlantToDao;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;

    public Plant getPlantById(String plantid) {
        return mapDaoToPlant(
                plantRepository.findById(Integer.valueOf(plantid))
                        .orElseThrow(PlantNotFoundException::new));
    }

    public List<Plant> getAllPlants() {
        List<Plant> plantList = new ArrayList<>();
        plantRepository.findAll().forEach(plantDao -> plantList.add(mapDaoToPlant(plantDao)));
        return plantList;
    }

    public void addPlant(Plant plant) {
        plantRepository.save(mapPlantToDao(plant));
    }

    public void removePlant(String plantid) {
        plantRepository.deleteById(Integer.valueOf(plantid));
    }

    public Tray getTrayById(String trayId) {
        return Optional.of(getAllTrays().get(Integer.valueOf(trayId)))
                .orElseThrow(TrayNotFoundException::new);
    }

    public List<Tray> getAllTrays() {
        List<Plant> allPlants = getAllPlants();

        Map<Integer, List<Plant>> groupedPlants = allPlants.stream()
                .collect(Collectors.groupingBy(Plant::getTrayId));

        return groupedPlants.entrySet().stream()
                .map(entry -> new Tray(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
