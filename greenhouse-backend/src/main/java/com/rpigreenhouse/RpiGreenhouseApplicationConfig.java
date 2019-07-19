package com.rpigreenhouse;

import com.rpigreenhouse.plants.BasilPlant;
import com.rpigreenhouse.plants.TomatoPlant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    @Component
    public class CustomizationPort implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

        @Override
        public void customize(ConfigurableWebServerFactory factory) {
            factory.setPort(8081);
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
