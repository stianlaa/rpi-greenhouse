package com.rpigreenhouse.plants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Plant {

    protected int trayId;

    protected String plantId;
    protected String plantType;
    protected LocalDateTime plantedDateTime;
    protected LocalDate expectedHarvestDate;
    protected Month idealGrowthMonthsFrom;
    protected Month idealGrowthMonthsTo;

    protected Integer seedWaterNeed;
    protected Integer matureWaterNeed;

    public Plant(int trayId) {
        this.plantId = UUID.randomUUID().toString();
        this.trayId = trayId;
        this.plantedDateTime = LocalDateTime.now();
    }

    public String getPlantId() {
        return plantId;
    }

    public String getStatus() {
        return String.format("%s %s: planted: %s\t\t", plantType, plantId.substring(0, 4), plantedDateTime.toString());
    }

    public Plant setIdealGrowthMonths(Month from, Month to) {
        this.setIdealGrowthMonthsFrom(from);
        this.setIdealGrowthMonthsTo(to);
        return this;
    }

    public List<Boolean> getIdealGrowthMonths() {
        if (idealGrowthMonthsFrom != null && idealGrowthMonthsTo != null) {
            return Arrays.stream(Month.values())
                    .map(month -> (month.getValue() > idealGrowthMonthsFrom.getValue() && month.getValue() < idealGrowthMonthsTo.getValue()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public float getMaturityPercentage() {
        Float secondsPassed = (float) ChronoUnit.SECONDS.between(this.plantedDateTime, LocalDateTime.now());
        Float secondsToPass = (float) ChronoUnit.SECONDS.between(this.plantedDateTime, this.expectedHarvestDate.atTime(12, 0));
        if (secondsPassed / secondsToPass > 1) {
            return 1.0f;
        } else if (secondsPassed / secondsToPass < 0) {
            return 0.0f;
        }
        return secondsPassed / secondsToPass;
    }
}
