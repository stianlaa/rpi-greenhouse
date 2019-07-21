package com.rpigreenhouse.controller;

import com.rpigreenhouse.plants.Plant;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlantTo {

    private String plantId;
    private String plantType;
    private LocalDateTime plantedDateTime;
    private LocalDate expectedHarvestDate;
    private List<Boolean> idealGrowthMonths;
    private Integer seedWaterNeed;
    private Integer matureWaterNeed;

    public PlantTo(Plant plant) {
        this.plantId = plant.getPlantId();
        this.plantType = plant.getPlantType();
        this.plantedDateTime = plant.getPlantedDateTime();
        this.expectedHarvestDate = plant.getExpectedHarvestDate();
        this.idealGrowthMonths = plant.getIdealGrowthMonths();
        this.seedWaterNeed = plant.getSeedWaterNeed();
        this.matureWaterNeed = plant.getMatureWaterNeed();
    }
}
