package com.rpigreenhouse.plants;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TomatoPlant extends Plant {
    private static final String TOMATO_NAME = "Tomatoplant";
    private static final Integer DAYS_TO_HARVEST = 150;
    private static final Integer SEED_WATER_NEED = 5;
    private static final Integer MATURE_WATER_NEED = 50;

    public TomatoPlant(int trayId) {
        super(trayId);
        this.plantType = TOMATO_NAME;
        this.expectedHarvestDate = LocalDate.now().plusDays(DAYS_TO_HARVEST);
        this.seedWaterNeed = SEED_WATER_NEED;
        this.matureWaterNeed = MATURE_WATER_NEED;
        this.plantImage = extractBytes(this.plantType + ".jpg"     );
    }
}
