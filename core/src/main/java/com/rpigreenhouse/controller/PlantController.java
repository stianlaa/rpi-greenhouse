package com.rpigreenhouse.controller;

import com.rpigreenhouse.dto.Plant;
import com.rpigreenhouse.dto.Tray;
import com.rpigreenhouse.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("greenhouse/")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @GetMapping("plant/{plantid}")
    public Plant getPlantById(@PathVariable String plantid) {
        return plantService.getPlantById(plantid);
    }

    @GetMapping("allplants")
    public List<Plant> getAllPlants() {
        return plantService.getAllPlants();
    }

    @PostMapping("plant")
    public void addPlant(@RequestBody Plant plant) {
        plantService.addPlant(plant);
    }

    @DeleteMapping("plant/{plantid}")
    public void deletePlant(@PathVariable String plantid) {
        plantService.removePlant(plantid);
    }

    @GetMapping("tray/{trayid}")
    public Tray getTrayById(@PathVariable String trayid) {
        return plantService.getTrayById(trayid);
    }

    @GetMapping("alltrays")
    public List<Tray> getAllTrays() {
        return plantService.getAllTrays();
    }

}
