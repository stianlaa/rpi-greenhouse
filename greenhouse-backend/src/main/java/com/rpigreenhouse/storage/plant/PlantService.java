package com.rpigreenhouse.storage.plant;

import com.rpigreenhouse.exceptions.PlantNotFoundException;
import com.rpigreenhouse.plants.Plant;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlantService {

    PlantRepository plantRepository;
    PlantTypeImageRepository plantImageRepository;

    public PlantService(PlantRepository plantRepository,
                        PlantTypeImageRepository plantImageRepository) {
        this.plantRepository = plantRepository;
        this.plantImageRepository = plantImageRepository;
    }

    public List<Plant> getAllPlants() {
        List<PlantDAO> storedPlants = new ArrayList<>();
        plantRepository.findAll().forEach(storedPlants::add);

        Map<String, byte[]> storedPlantTypeImages = new HashMap<>();
        plantImageRepository.findAll().forEach(stored -> storedPlantTypeImages.put(stored.getPlantType(), stored.getPlantTypeImage()));

        storedPlants.forEach(plantDAO -> plantDAO.setPlantTypeImage(storedPlantTypeImages.get(plantDAO.getPlantType())));

        return storedPlants.stream()
                .map(PlantDAO::mapToPlant)
                .collect(Collectors.toList());
    }

    public Plant getPlant(Integer id) {
        Optional<PlantDAO> result = plantRepository.findById(id);
        if (result.isPresent()) {
            Optional<PlantTypeImageDAO> imageResult = plantImageRepository.findByPlantType(result.get().getPlantType());
            if (imageResult.isPresent()) {
                return result.get()
                        .setPlantTypeImage(imageResult.get().getPlantTypeImage())
                        .mapToPlant();
            }
            return null;
        } else {
            throw new PlantNotFoundException();
        }
    }

    public Map<String, byte[]> getAllPlantTypeImages() {
        Map<String, byte[]> storedPlantTypeImages = new HashMap<>();
        plantImageRepository.findAll().forEach(stored -> storedPlantTypeImages.put(stored.getPlantType(), stored.getPlantTypeImage()));
        return storedPlantTypeImages;
    }

    public void saveOrUpdatePlant(Plant plant) {
        plantRepository.save(new PlantDAO(plant));
        plantImageRepository.save(new PlantTypeImageDAO(plant));
    }

    public void delete(Integer id) {
        plantRepository.deleteById(id);
    }
}
