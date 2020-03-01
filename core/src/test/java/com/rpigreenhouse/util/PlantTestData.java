package com.rpigreenhouse.util;

import com.rpigreenhouse.dto.Plant;
import com.rpigreenhouse.storage.plant.PlantType;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class PlantTestData {

    public static Plant createTestPlant(Integer trayId, PlantType plantType) {
        return Plant.builder()
                .trayId(trayId)
                .plantType(plantType.toString())
                .expectedHarvestDate(LocalDate.now().plusDays(90))
                .plantedDateTime(LocalDateTime.now())
                .idealGrowthStart(null)
                .idealGrowthEnd(null)
                .seedWaterNeed(30)
                .matureWaterNeed(60)
                .build();
    }
}
