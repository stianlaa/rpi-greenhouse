package com.rpigreenhouse.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class PlantUtil {

    public static float getMaturityPercentage(LocalDateTime plantedDateTime, LocalDate expectedHarvestDate) {
        Float secondsPassed = (float) ChronoUnit.SECONDS.between(plantedDateTime, LocalDateTime.now());
        Float secondsToPass = (float) ChronoUnit.SECONDS.between(plantedDateTime, expectedHarvestDate.atTime(12, 0));
        if (secondsPassed / secondsToPass > 1) {
            return 1.0f;
        } else if (secondsPassed / secondsToPass < 0) {
            return 0.0f;
        }
        return secondsPassed / secondsToPass;
    }
}
