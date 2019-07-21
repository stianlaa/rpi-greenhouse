package com.rpigreenhouse.controller;

import com.rpigreenhouse.exceptions.InvalidRequestException;
import com.rpigreenhouse.exceptions.PlantNotFoundException;
import com.rpigreenhouse.plants.Plant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rpigreenhouse.GreenhouseLogger.debugLog;

@RestController
@RequestMapping("rest/")
public class PlantController {

    private GreenhouseStorage greenhouseStorage;

    @Autowired
    public PlantController(GreenhouseStorage greenhouseStorage) {
        this.greenhouseStorage = greenhouseStorage;
    }

    @GetMapping("getplants")
    public List<Plant> getPlants() {
        debugLog("received request for all plants");
        return greenhouseStorage.getPlants();
    }

    @GetMapping("getplant/{plantid}")
    public Plant getPlantById(@PathVariable String plantid) {
        debugLog(String.format("received request for plant: {}", plantid));

        if (plantid.trim().isEmpty()) throw new InvalidRequestException("The request contained no id");
        return greenhouseStorage.getPlant(plantid)
                .orElseThrow(PlantNotFoundException::new);
    }
}
