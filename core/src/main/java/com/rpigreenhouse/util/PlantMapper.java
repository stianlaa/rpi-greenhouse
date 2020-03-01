package com.rpigreenhouse.util;

import com.rpigreenhouse.dao.PlantDAO;
import com.rpigreenhouse.dto.Plant;
import lombok.experimental.UtilityClass;

import static com.rpigreenhouse.util.PlantUtil.getMaturityPercentage;

@UtilityClass
public class PlantMapper {

    public static Plant mapDaoToPlant(PlantDAO plantDAO) {
        return Plant.builder()
                .trayId(plantDAO.getTrayId())
                .plantId(plantDAO.getId())
                .plantType(plantDAO.getPlantType())
                .plantedDateTime(plantDAO.getPlantedDateTime())
                .expectedHarvestDate(plantDAO.getExpectedHarvestDate())
                .idealGrowthStart(plantDAO.getIdealGrowthStart())
                .idealGrowthEnd(plantDAO.getIdealGrowthEnd())
                .seedWaterNeed(plantDAO.getSeedWaterNeed())
                .matureWaterNeed(plantDAO.getMatureWaterNeed())
                .maturityPercentage(
                        getMaturityPercentage(
                                plantDAO.getPlantedDateTime(),
                                plantDAO.getExpectedHarvestDate()))
                .build();
    }

    public static PlantDAO mapPlantToDao(Plant plant) {
        return PlantDAO.builder()
                .trayId(plant.getTrayId())
                .plantId(plant.getPlantId())
                .plantType(plant.getPlantType())
                .plantedDateTime(plant.getPlantedDateTime())
                .expectedHarvestDate(plant.getExpectedHarvestDate())
                .idealGrowthStart(plant.getIdealGrowthStart())
                .idealGrowthEnd(plant.getIdealGrowthEnd())
                .seedWaterNeed(plant.getSeedWaterNeed())
                .matureWaterNeed(plant.getMatureWaterNeed())
                .build();
    }
}

