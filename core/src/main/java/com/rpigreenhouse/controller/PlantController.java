package com.rpigreenhouse.controller;

import com.rpigreenhouse.exceptions.PlantNotFoundException;
import com.rpigreenhouse.exceptions.TrayNotFoundException;
import com.rpigreenhouse.greenhouse.Tray;
import com.rpigreenhouse.storage.GreenhouseStorage;
import com.rpigreenhouse.storage.plant.Plant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rpigreenhouse.GreenhouseLogger.debugLog;


@RestController
@RequestMapping("rest/greenhouse/")
@RequiredArgsConstructor
public class PlantController {

    private final GreenhouseStorage greenhouseStorage;

    @GetMapping("getplants")
    public List<PlantTo> getPlants() {
        debugLog("received request for all plants");
        return greenhouseStorage.getPlants().stream().map(PlantTo::new).collect(Collectors.toList());
    }

    @GetMapping("gettrayswithplants")
    public List<List<PlantTo>> getTraysWithPlants() {
        List<Plant> plantList = greenhouseStorage.getPlants();
        Set<Integer> trayIds = plantList.stream().map(Plant::getTrayId).collect(Collectors.toSet());
        List<List<PlantTo>> traysWithPlants = new ArrayList<>(new ArrayList<>());

        for (Integer trayId : trayIds) {
            traysWithPlants.add(
                    plantList.stream().filter(plant -> plant.getTrayId() == trayId).map(PlantTo::new).collect(Collectors.toList())
            );
        }
        debugLog("received request for all plants");
        return traysWithPlants;
    }

    @GetMapping("getplant/{plantid}")
    public PlantTo getPlantById(@PathVariable String plantid) {
        debugLog(String.format("received request for plant: %s", plantid));

        return new PlantTo(greenhouseStorage.getPlant(plantid)
                .orElseThrow(PlantNotFoundException::new));
    }

    @PostMapping("addplant")
    public void addPlant(@PathVariable Plant plant) {
        debugLog("received request to add new plant");
        greenhouseStorage.addPlant(plant);
    }

    @GetMapping(value = "getplantidsfortray/{trayid}")
    public List<String> getPlantIdsForTray(@PathVariable Integer trayid) {
        debugLog(String.format("received request for plantids for tray: %s", trayid));
        Tray specifiedTray = greenhouseStorage.getTraysWithPlants().stream()
                .filter(tray -> trayid.equals(tray.getTrayId())).findFirst()
                .orElseThrow(TrayNotFoundException::new);
        return specifiedTray.getPlants().stream().map(Plant::getPlantId).collect(Collectors.toList());
    }
}
