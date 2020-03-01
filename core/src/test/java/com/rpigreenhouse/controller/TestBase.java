package com.rpigreenhouse.controller;

import com.rpigreenhouse.TestConfig;
import com.rpigreenhouse.storage.plant.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestConfig.class)
public abstract class TestBase {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    protected PlantRepository plantRepository;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected URI createRequestUri(String path) {
        return UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port)
                .path(path)
                .build().toUri();
    }

    protected URI createRequestUri(String path, String paramKey, String paramValue) {
        return UriComponentsBuilder
                .fromHttpUrl("http://localhost:" + port)
                .path(path)
                .queryParam(paramKey, paramValue)
                .build().toUri();
    }
}
