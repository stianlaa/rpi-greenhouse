package com.rpigreenhouse.measurements;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DispenserVolumeSensor implements Sensor {

    public Double sample() {
        return 0.0;
    }

    // todo implement as in POC in jupyter notebook

}
