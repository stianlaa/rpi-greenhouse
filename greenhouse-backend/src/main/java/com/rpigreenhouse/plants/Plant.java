package com.rpigreenhouse.plants;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Plant {

    @Id
    @GeneratedValue
    protected int dbId;

    protected String plantId;
    protected String plantType;
    protected LocalDateTime plantedDateTime;
    protected LocalDate expectedHarvestDate;
    protected List<Boolean> idealGrowthMonths;

    protected Integer seedWaterNeed;
    protected Integer matureWaterNeed;

    protected byte[] imageOfPlant; // todo
    protected Integer idealTemperature; // todo
    protected Duration idealLightExposure; // todo


    public Plant() {
        this.plantId = UUID.randomUUID().toString();
        this.plantedDateTime = LocalDateTime.now();
    }

    public void setIdealGrowthMonths(Month from, Month to) {
        this.idealGrowthMonths = Arrays.stream(Month.values()).map(month -> (month.getValue() > from.getValue() && month.getValue() < to.getValue())).
                collect(Collectors.toList());
    }

    public String getPlantId() {
        return plantId;
    }

    public String getStatus() {
        return String.format("%s %s: planted: %s\t\t", plantType, plantId.substring(0, 4), plantedDateTime.toString());
    }
}
