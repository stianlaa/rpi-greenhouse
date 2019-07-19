package com.rpigreenhouse.greenhouse;

import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Greenhouse {
    private GreenhouseStorage greenhouseStorage;

    @Autowired
    public void Greenhouse(GreenhouseStorage greenhouseStorage) {
        this.greenhouseStorage = greenhouseStorage;
    }
}
