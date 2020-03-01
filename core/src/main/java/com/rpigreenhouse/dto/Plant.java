package com.rpigreenhouse.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Plant {

    protected int trayId;
    private int plantId;
    private String plantType;
    private LocalDateTime plantedDateTime;
    private LocalDate expectedHarvestDate;
    private LocalDate idealGrowthStart;
    private LocalDate idealGrowthEnd;
    private Integer seedWaterNeed;
    private Integer matureWaterNeed;
    private Float maturityPercentage;

}
