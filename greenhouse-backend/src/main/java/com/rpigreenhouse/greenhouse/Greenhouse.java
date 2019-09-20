package com.rpigreenhouse.greenhouse;

import com.rpigreenhouse.managers.sensor.SensorManager;
import com.rpigreenhouse.managers.watering.WaterManager;
import com.rpigreenhouse.plants.BasilPlant;
import com.rpigreenhouse.plants.TomatoPlant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Component
public class Greenhouse {

    private LocalDateTime firstWatering;
    private Long wateringInterval;

    private GreenhouseStorage greenhouseStorage;
    private WaterManager waterManager;
    private SensorManager sensorManager;

    @Autowired
    public void Greenhouse(GreenhouseStorage greenhouseStorage,
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

//        if (firstWatering != null && wateringInterval != null)
//            waterManager.startWaterCheckingSchedule(firstWatering, wateringInterval);
        sensorManager.startSensorScedules(1000000L);
    }

    private void addStartupMockData() {
        greenhouseStorage.addPlant(new TomatoPlant(1).setIdealGrowthMonths(Month.APRIL, Month.AUGUST));
        greenhouseStorage.addPlant(new TomatoPlant(1).setIdealGrowthMonths(Month.APRIL, Month.AUGUST));
        greenhouseStorage.addPlant(new TomatoPlant(1).setIdealGrowthMonths(Month.APRIL, Month.AUGUST));
        greenhouseStorage.addPlant(new BasilPlant(2).setIdealGrowthMonths(Month.MARCH, Month.SEPTEMBER));
        greenhouseStorage.addPlant(new BasilPlant(2).setIdealGrowthMonths(Month.MARCH, Month.SEPTEMBER));
    }

    private LocalDateTime findNextWateringTime(Integer firstWateringHour, Integer firstWateringMinute) {
        if (LocalDate.now().atTime(firstWateringHour, firstWateringMinute).isBefore(LocalDateTime.now())) {
            return LocalDate.now().atTime(firstWateringHour, firstWateringMinute).plusDays(1);
        } else {
            return LocalDate.now().atTime(firstWateringHour, firstWateringMinute);
        }
    }
}