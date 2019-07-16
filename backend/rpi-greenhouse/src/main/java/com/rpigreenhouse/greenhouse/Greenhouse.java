package com.rpigreenhouse.greenhouse;

import com.rpigreenhouse.storage.GreenhouseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Greenhouse {
    GreenhouseStorage greenhouseStorage;

    @Autowired
    public void Greenhouse(GreenhouseStorage greenhouseStorage) {
        this.greenhouseStorage = greenhouseStorage;
    }

    public void printStatus() { // todo replace
        Integer i = 0;
        while( i < 10) {
            i++;
            System.out.println(greenhouseStorage.getStatus());
        }
    }

}
