package com.rpigreenhouse.plants;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Plant {

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

    public Integer calculateWaterNeed(LocalDate atDate) { // todo move to watermanager when created
        if (atDate.isAfter(expectedHarvestDate)) {
            return matureWaterNeed;
        } else if (atDate.isBefore(plantedDateTime.toLocalDate())) {
            return seedWaterNeed;
        }
        Float daysPassed = (float) ChronoUnit.DAYS.between(plantedDateTime.toLocalDate(), atDate);
        Float matureDuration = (float) ChronoUnit.DAYS.between(plantedDateTime.toLocalDate(), expectedHarvestDate);
        return Math.round(seedWaterNeed + (matureWaterNeed - seedWaterNeed) * (daysPassed / matureDuration));
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
