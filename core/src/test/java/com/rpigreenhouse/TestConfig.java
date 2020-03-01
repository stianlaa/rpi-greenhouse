package com.rpigreenhouse;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@EnableAutoConfiguration
@ContextConfiguration(classes = GreenhouseConfig.class)
public class TestConfig {

    @Bean
    protected TestRestTemplate createTestRestTemplate() {
        return new TestRestTemplate();
    }
}
