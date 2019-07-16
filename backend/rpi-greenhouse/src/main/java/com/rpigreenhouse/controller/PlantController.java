package com.rpigreenhouse.controller;

import com.rpigreenhouse.greenhouse.Greenhouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlantController {

    private Greenhouse greenhouse;

    @Autowired
    public PlantController(Greenhouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    @RequestMapping("/")
    public String getPlantStatus() { // todo return actual status after testing with deploy
        greenhouse.printStatus();
        return "This is a test for nginx deployment";
    }
}
