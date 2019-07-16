package com.rpigreenhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(value = {RpiGreenhouseApplicationConfig.class})
@SpringBootApplication
public class RpiGreenhouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpiGreenhouseApplication.class, args);
    }
}

