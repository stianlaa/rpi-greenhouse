package com.rpigreenhouse.controller;

import com.rpigreenhouse.consumer.WeatherConsumer;
import com.rpigreenhouse.consumer.WeatherStatus;
import com.rpigreenhouse.exceptions.InvalidRequestException;
import com.rpigreenhouse.exceptions.PlantNotFoundException;
import com.rpigreenhouse.managers.watering.WaterManager;
import com.rpigreenhouse.plants.BasilPlant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.rpigreenhouse.GreenhouseLogger.debugLog;

@RestController
@RequestMapping("rest/")
public class PlantController {

    private GreenhouseStorage greenhouseStorage;
    private WaterManager waterManager;
    private WeatherConsumer weatherConsumer;

    @Autowired
    public PlantController(GreenhouseStorage greenhouseStorage,
                           WaterManager waterManager,
                           WeatherConsumer weatherConsumer) {
        this.greenhouseStorage = greenhouseStorage;
        this.waterManager = waterManager;
        this.weatherConsumer = weatherConsumer;
    }

    @GetMapping("getplants")
    public List<PlantTo> getPlants() {
        debugLog("received request for all plants");
        return greenhouseStorage.getPlants().stream().map(PlantTo::new).collect(Collectors.toList());
    }

    @GetMapping("getplant/{plantid}")
    public PlantTo getPlantById(@PathVariable String plantid) {
        debugLog(String.format("received request for plant: %s", plantid));

        if (plantid.trim().isEmpty()) throw new InvalidRequestException("The request contained no id");
        return new PlantTo(greenhouseStorage.getPlant(plantid)
                .orElseThrow(PlantNotFoundException::new));
    }

    @PostMapping("addplant")
    public void addPlant() {
        greenhouseStorage.addPlant(new BasilPlant(2));
        debugLog("received request to add new plant");
    }

    @GetMapping("nextwatering") // todo move to statuscontroller?
    public LocalDateTime getNextWaterTime() {
        return waterManager.getNextWaterTime();
    }

    @CrossOrigin
    @RequestMapping(value = "getweather", method = RequestMethod.GET, produces = "application/json")
    public WeatherStatus getCurrentWeather() {
        return weatherConsumer.fetchWeatherForecast();
    }
}
