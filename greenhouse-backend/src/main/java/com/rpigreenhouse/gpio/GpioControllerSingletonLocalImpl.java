package com.rpigreenhouse.gpio;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.rpigreenhouse.GreenhouseLogger.errorLog;

@Component
@Profile("local")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GpioControllerSingletonLocalImpl implements GpioControllerSingleton {

    private final Map<Integer, Boolean> provisionedPins = new HashMap<>();

    public GpioControllerSingletonLocalImpl() {
        errorLog("Local GPIO BEAN WAS CREATED");
    }

    public void setPin(int address, Boolean state) {
        errorLog(String.format("Put the pin %d to state %b", address, state)); // todo replace with sensible level
        provisionedPins.put(address, state);
    }

    public Boolean getPinState(int address) {
        return provisionedPins.get(address);
    }

}