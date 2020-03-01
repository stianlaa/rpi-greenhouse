package com.rpigreenhouse.controller;

import com.rpigreenhouse.dao.PlantDAO;
import com.rpigreenhouse.dto.Plant;
import com.rpigreenhouse.storage.plant.PlantType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import static com.rpigreenhouse.util.PlantMapper.mapPlantToDao;
import static com.rpigreenhouse.util.PlantTestData.createTestPlant;


public class PlantControllerTest extends TestBase {

    private List<PlantDAO> populateDatabase() {
        List<PlantDAO> storedPlants = new ArrayList();
        storedPlants.add(plantRepository.save(mapPlantToDao(createTestPlant(1, PlantType.TOMATO_PLANT))));
        storedPlants.add(plantRepository.save(mapPlantToDao(createTestPlant(2, PlantType.BASIL_PLANT))));
        storedPlants.add(plantRepository.save(mapPlantToDao(createTestPlant(2, PlantType.BASIL_PLANT))));
        return storedPlants;
    }

    @Test
    public void shouldGetPlant() {
        List<PlantDAO> storedPlants = populateDatabase();

        restTemplate.exchange(createRequestUri("greenhouse/plant/" + storedPlants.get(0).getId()),
                HttpMethod.GET,
                null,
                Plant.class);
    }

}
