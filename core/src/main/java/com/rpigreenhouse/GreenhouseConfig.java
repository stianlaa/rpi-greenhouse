package com.rpigreenhouse;

import com.rpigreenhouse.managers.sensor.SensorManager;
import com.rpigreenhouse.managers.watering.WaterManager;
import com.rpigreenhouse.storage.GreenhouseStorage;
import com.rpigreenhouse.storage.plant.Plant;
import com.rpigreenhouse.storage.plant.PlantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Configuration
public class GreenhouseConfig {

    private LocalDateTime firstWatering;

    private Long wateringInterval;
    private GreenhouseStorage greenhouseStorage;
    private WaterManager waterManager;
    private SensorManager sensorManager;

    public GreenhouseConfig(GreenhouseStorage greenhouseStorage,
                           WaterManager waterManager,
                           SensorManager sensorManager,
                           @Value("${greenhouse.firstWateringHour}") Integer firstWateringHour,
                           @Value("${greenhouse.firstWateringMinute}") Integer firstWateringMinute,
                           @Value("${greenhouse.wateringInterval}") Long wateringInterval) {
        this.greenhouseStorage = greenhouseStorage;
        this.waterManager = waterManager;
        this.sensorManager = sensorManager;
        this.wateringInterval = wateringInterval;
        this.firstWatering = findNextWateringTime(firstWateringHour, firstWateringMinute);

        addStartupMockData();

        if (firstWatering != null && wateringInterval != null) {
            waterManager.startWaterCheckingSchedule(firstWatering, wateringInterval);
        }
        sensorManager.startSensorScedules();
    }

    private void addStartupMockData() {
        greenhouseStorage.addPlant(Plant.builder()
                .trayId(1)
                .plantType(PlantType.TOMATO_PLANT)
                .expectedHarvestDate(LocalDate.now().plusDays(90))
                .plantedDateTime(LocalDateTime.now())
                .idealGrowthMonthsFrom(Month.APRIL)
                .idealGrowthMonthsFrom(Month.AUGUST)
                .seedWaterNeed(30)
                .matureWaterNeed(60)
                .build());

        greenhouseStorage.addPlant(Plant.builder()
                .trayId(2)
                .plantType(PlantType.BASIL_PLANT)
                .expectedHarvestDate(LocalDate.now().plusDays(90))
                .plantedDateTime(LocalDateTime.now())
                .idealGrowthMonthsFrom(Month.APRIL)
                .idealGrowthMonthsFrom(Month.AUGUST)
                .seedWaterNeed(30)
                .matureWaterNeed(60)
                .build());

        greenhouseStorage.addPlant(Plant.builder()
                .trayId(2)
                .plantType(PlantType.BASIL_PLANT)
                .expectedHarvestDate(LocalDate.now().plusDays(90))
                .plantedDateTime(LocalDateTime.now())
                .idealGrowthMonthsFrom(Month.APRIL)
                .idealGrowthMonthsFrom(Month.AUGUST)
                .seedWaterNeed(30)
                .matureWaterNeed(60)
                .build());
    }

    private LocalDateTime findNextWateringTime(Integer firstWateringHour, Integer firstWateringMinute) {
        if (LocalDate.now().atTime(firstWateringHour, firstWateringMinute).isBefore(LocalDateTime.now())) {
            return LocalDate.now().atTime(firstWateringHour, firstWateringMinute).plusDays(1);
        } else {
            return LocalDate.now().atTime(firstWateringHour, firstWateringMinute);
        }
    }
}
