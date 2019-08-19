package com.rpigreenhouse.gpio;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

import static com.rpigreenhouse.GreenhouseLogger.infoLog;

@Component
@Profile("local")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GpioControllerSingletonLocalImpl implements GpioControllerSingleton {

    private final Map<Integer, Boolean> provisionedPins = new HashMap<>();

    public GpioControllerSingletonLocalImpl() {
    }

    @PreDestroy
    public void destroy() {
        setAllPinsLow();
    }

    @Override
    public void setPin(int address, Boolean state) {
        infoLog(String.format("Local test without hardware: put the pin %d to state %b", address, state));
        provisionedPins.put(address, state);
    }

    @Override
    public Boolean getPinState(int address) {
        return provisionedPins.get(address);
    }

    @Override
    public void setAllPinsLow() {
        infoLog("Setting all pins low");
        for (Integer pinAddress : provisionedPins.keySet()) {
            provisionedPins.put(pinAddress, false);
        }
        System.out.println(provisionedPins);
    }
}