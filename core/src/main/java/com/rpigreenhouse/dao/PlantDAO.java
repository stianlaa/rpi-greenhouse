package com.rpigreenhouse.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Data
@Entity(name = "PLANT_TABLE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantDAO {

    @Id
    @GeneratedValue
    private int id;
    private int trayId;
    private int plantId;

    private String plantType;
    private LocalDateTime plantedDateTime;
    private LocalDate expectedHarvestDate;
    private LocalDate idealGrowthStart;
    private LocalDate idealGrowthEnd;
    private Month idealGrowthMonthsTo;
    private Integer seedWaterNeed;
    private Integer matureWaterNeed;
}
