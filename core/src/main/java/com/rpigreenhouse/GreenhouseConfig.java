package com.rpigreenhouse;

import com.rpigreenhouse.dto.Plant;
import com.rpigreenhouse.managers.sensor.SensorManager;
import com.rpigreenhouse.managers.watering.WaterManager;
import com.rpigreenhouse.service.PlantService;
import com.rpigreenhouse.storage.plant.PlantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@ComponentScan
public class GreenhouseConfig {

    private LocalDateTime nextWatering;

    private PlantService plantService;
    private WaterManager waterManager;
    private SensorManager sensorManager;

    public GreenhouseConfig(PlantService plantService,
                            WaterManager waterManager,
                            SensorManager sensorManager,
                            @Value("${greenhouse.wateringTime}") String wateringTime) {
        this.plantService = plantService;
        this.waterManager = waterManager;
        this.sensorManager = sensorManager;
        this.nextWatering = findNextWateringTime(LocalTime.parse(wateringTime));

        addStartupMockData();

        if (wateringTime != null && this.nextWatering != null) {
            waterManager.startWaterCheckingSchedule(nextWatering);
        }
        sensorManager.startSensorScedules();
    }

    private void addStartupMockData() {
        plantService.addPlant(Plant.builder()
                .trayId(1)
                .plantType(PlantType.TOMATO_PLANT.toString())
                .expectedHarvestDate(LocalDate.now().plusDays(90))
                .plantedDateTime(LocalDateTime.now())
                .idealGrowthStart(null)
                .idealGrowthEnd(null)
                .seedWaterNeed(30)
                .matureWaterNeed(60)
                .build());

        plantService.addPlant(Plant.builder()
                .trayId(2)
                .plantType(PlantType.BASIL_PLANT.toString())
                .expectedHarvestDate(LocalDate.now().plusDays(90))
                .plantedDateTime(LocalDateTime.now())
                .idealGrowthStart(null)
                .idealGrowthEnd(null)
                .seedWaterNeed(30)
                .matureWaterNeed(60)
                .build());

        plantService.addPlant(Plant.builder()
                .trayId(2)
                .plantType(PlantType.BASIL_PLANT.toString())
                .expectedHarvestDate(LocalDate.now().plusDays(90))
                .plantedDateTime(LocalDateTime.now())
                .idealGrowthStart(null)
                .idealGrowthEnd(null)
                .seedWaterNeed(30)
                .matureWaterNeed(60)
                .build());
    }

    private LocalDateTime findNextWateringTime(LocalTime wateringTime) {
        if (LocalTime.now().isAfter(wateringTime)) {
            return wateringTime.atDate(LocalDate.now().plusDays(1));
        } else {
            return wateringTime.atDate(LocalDate.now());
        }
    }
}
