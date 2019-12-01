package com.rpigreenhouse.storage.plant;

import com.rpigreenhouse.exceptions.PlantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;

    public List<Plant> getAllPlants() {
        List<PlantDao> storedPlants = new ArrayList<>();
        plantRepository.findAll().forEach(storedPlants::add);

        return storedPlants.stream()
                .map(PlantDao::mapToPlant)
                .collect(Collectors.toList());
    }

    public Plant getPlant(Integer id) {
        Optional<PlantDao> result = plantRepository.findById(id);
        if (result.isPresent()) {
            return null;
        } else {
            throw new PlantNotFoundException();
        }
    }

    public void saveOrUpdatePlant(Plant plant) {
        plantRepository.save(new PlantDao(plant));
    }

    public void delete(Integer id) {
        plantRepository.deleteById(id);
    }
}
