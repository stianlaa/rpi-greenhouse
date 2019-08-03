package com.rpigreenhouse;

import com.rpigreenhouse.storage.GreenhouseStorage;
import com.rpigreenhouse.storage.plant.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class RpiGreenhouseApplicationConfig {

    @Autowired
    private PlantService plantService;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    GreenhouseStorage plantStatusStorage() {
        return new GreenhouseStorage(plantService);
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
