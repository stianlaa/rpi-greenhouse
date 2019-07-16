package com.rpigreenhouse;

import com.rpigreenhouse.plants.BasilPlant;
import com.rpigreenhouse.plants.TomatoPlant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan
public class RpiGreenhouseApplicationConfig {

    @Bean
    GreenhouseStorage plantStatusStorage() {

        GreenhouseStorage plantStatusStorage = new GreenhouseStorage();

        // initializes plantStatusStorage with plants // todo replace with fetching from database
        plantStatusStorage.addTray(Arrays.asList(new TomatoPlant(), new TomatoPlant()));
        plantStatusStorage.addTray(Arrays.asList(new BasilPlant(), new BasilPlant(), new BasilPlant()));

        return plantStatusStorage;
    }
}
