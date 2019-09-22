package com.rpigreenhouse.plants;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BasilPlant extends Plant {
    private static final String BASIL_NAME = "Basilplant";
    private static final Integer DAYS_TO_HARVEST = 75;
    private static final Integer SEED_WATER_NEED = 30;
    private static final Integer MATURE_WATER_NEED = 50;

    public BasilPlant(int trayId) {
        super(trayId);
        this.plantType = BASIL_NAME;
        this.expectedHarvestDate = LocalDate.now().plusDays(DAYS_TO_HARVEST);
        this.seedWaterNeed = SEED_WATER_NEED;
        this.matureWaterNeed = MATURE_WATER_NEED;
    }
}
