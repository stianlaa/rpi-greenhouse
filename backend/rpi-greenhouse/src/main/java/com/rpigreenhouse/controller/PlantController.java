package com.rpigreenhouse.controller;

import com.rpigreenhouse.Exceptions.InvalidRequestException;
import com.rpigreenhouse.Exceptions.PlantNotFoundException;
import com.rpigreenhouse.greenhouse.Greenhouse;
import com.rpigreenhouse.plants.Plant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rest/")
public class PlantController {

    private Greenhouse greenhouse;
    private GreenhouseStorage greenhouseStorage;

    @Autowired
    public PlantController(Greenhouse greenhouse,
                           GreenhouseStorage greenhouseStorage) {
        this.greenhouse = greenhouse;
        this.greenhouseStorage = greenhouseStorage;
    }

    @GetMapping("getplants")
    public List<Plant> getPlants() {
        return greenhouseStorage.getPlants();
    }

    @GetMapping("getplant/{plantid}")
    public Plant getPlantById(@PathVariable String plantid) {
        if (plantid.trim().isEmpty()) throw new InvalidRequestException("The request contained no id");
        return greenhouseStorage.getPlant(plantid)
                .orElseThrow(PlantNotFoundException::new);
    }
}
