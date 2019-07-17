package com.rpigreenhouse;

import com.rpigreenhouse.plants.BasilPlant;
import com.rpigreenhouse.plants.TomatoPlant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.Month;
import java.util.Arrays;

@Configuration
@ComponentScan
public class RpiGreenhouseApplicationConfig {


    @Bean
    @Scope("singleton")
    GreenhouseStorage plantStatusStorage() {

        GreenhouseStorage plantStatusStorage = new GreenhouseStorage();

        // initializes plantStatusStorage with plants // todo replace with fetching from database
        plantStatusStorage.addTray(Arrays.asList(new TomatoPlant(), new TomatoPlant()));
        plantStatusStorage.addTray(Arrays.asList(new BasilPlant(), new BasilPlant(), new BasilPlant()));

        plantStatusStorage.getPlants().get(0).setIdealGrowthMonths(Month.MAY, Month.SEPTEMBER);

        return plantStatusStorage;
    }
}
