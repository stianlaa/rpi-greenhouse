package com.rpigreenhouse.storage.plant;

import com.rpigreenhouse.exceptions.PlantNotFoundException;
import com.rpigreenhouse.plants.Plant;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlantService {

    PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> getAllPlants() {
        List<PlantDAO> storedPlants = new ArrayList<>();
        plantRepository.findAll().forEach(storedPlants::add);

        return storedPlants.stream()
                .map(PlantDAO::mapToPlant)
                .collect(Collectors.toList());
    }

    public Plant getPlant(Integer id) {
        Optional<PlantDAO> result = plantRepository.findById(id);
        if (result.isPresent()) {
            return null;
        } else {
            throw new PlantNotFoundException();
        }
    }

    public void saveOrUpdatePlant(Plant plant) {
        plantRepository.save(new PlantDAO(plant));
    }

    public void delete(Integer id) {
        plantRepository.deleteById(id);
    }
}
