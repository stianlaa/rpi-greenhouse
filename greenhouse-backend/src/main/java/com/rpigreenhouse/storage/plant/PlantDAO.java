package com.rpigreenhouse.storage.plant;

import com.rpigreenhouse.plants.Plant;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Data
@Entity(name = "PLANT_TABLE")
public class PlantDAO {

    @Id
    @GeneratedValue
    private int id;

    private int trayId;

    private String plantId;
    private String plantType;
    private LocalDateTime plantedDateTime;
    private LocalDate expectedHarvestDate;
    private Month idealGrowthMonthsFrom;
    private Month idealGrowthMonthsTo;
    private Integer seedWaterNeed;
    private Integer matureWaterNeed;

    public PlantDAO() {
        super();
    }

    public PlantDAO(Plant plant) {
        this.plantId = plant.getPlantId();
        this.trayId = plant.getTrayId();

        this.plantType = plant.getPlantType();
        this.plantedDateTime = plant.getPlantedDateTime();
        this.expectedHarvestDate = plant.getExpectedHarvestDate();
        this.idealGrowthMonthsFrom = plant.getIdealGrowthMonthsFrom();
        this.idealGrowthMonthsTo = plant.getIdealGrowthMonthsTo();
        this.seedWaterNeed = plant.getSeedWaterNeed();
        this.matureWaterNeed = plant.getMatureWaterNeed();
    }

    public Plant mapToPlant() {
        return Plant.builder()
                .trayId(this.trayId)
                .plantId(this.plantId)
                .plantedDateTime(this.plantedDateTime)
                .expectedHarvestDate(this.expectedHarvestDate)
                .idealGrowthMonthsFrom(this.getIdealGrowthMonthsFrom())
                .idealGrowthMonthsTo(this.getIdealGrowthMonthsTo())
                .seedWaterNeed(this.seedWaterNeed)
                .matureWaterNeed(this.matureWaterNeed)
                .build();
    }
}