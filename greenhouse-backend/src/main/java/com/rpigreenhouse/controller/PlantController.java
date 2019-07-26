package com.rpigreenhouse.controller;

import com.rpigreenhouse.consumer.WeatherConsumer;
import com.rpigreenhouse.consumer.WeatherStatus;
import com.rpigreenhouse.exceptions.InvalidRequestException;
import com.rpigreenhouse.exceptions.PlantNotFoundException;
import com.rpigreenhouse.managers.watering.WaterManager;
import com.rpigreenhouse.plants.BasilPlant;
import com.rpigreenhouse.plants.Plant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    @CrossOrigin
    @GetMapping("getplants")
    public List<PlantTo> getPlants() {
        debugLog("received request for all plants");
        return greenhouseStorage.getPlants().stream().map(PlantTo::new).collect(Collectors.toList());
    }

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("getplant/{plantid}")
    public PlantTo getPlantById(@PathVariable String plantid) {
        debugLog(String.format("received request for plant: %s", plantid));

        if (plantid.trim().isEmpty()) throw new InvalidRequestException("The request contained no id");
        return new PlantTo(greenhouseStorage.getPlant(plantid)
                .orElseThrow(PlantNotFoundException::new));
    }

    @CrossOrigin
    @PostMapping("addplant")
    public void addPlant() {
        greenhouseStorage.addPlant(new BasilPlant(2));
        debugLog("received request to add new plant");
    }

    @CrossOrigin
    @GetMapping("nextwatering") // todo move to statuscontroller?
    public LocalDateTime getNextWaterTime() {
        return waterManager.getNextWaterTime();
    }

    @CrossOrigin
    @RequestMapping(value = "getweather", method = RequestMethod.GET, produces = "application/json")
    public WeatherStatus getCurrentWeather() {
        return WeatherStatus.builder().temperature(5.0).humidity(2.0).cloudiness(4.5).build();
    }
//    public WeatherStatus getCurrentWeather() {
//        return weatherConsumer.fetchWeatherForecast();
//    }

}
