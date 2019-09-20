package com.rpigreenhouse.gpio;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.rpigreenhouse.GreenhouseLogger.infoLog;

@Component
@Profile("local")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GpioControllerSingletonLocalImpl implements GpioControllerSingleton {

    private Random random = new Random();

    private final Map<Integer, Boolean> provisionedPins = new HashMap<>();

    public GpioControllerSingletonLocalImpl() {
    }

    @PreDestroy
    public void destroy() {
        setAllPinsLow();
    }

    @Override
    public void setPin(OutputPin pin, Boolean state) {
        Integer address = pin.addr();
        provisionedPins.put(address, state);
    }

    @Override
    public Boolean getPinState(InputPin pin) {
        return random.nextBoolean();
    }

    @Override
    public void setAllPinsLow() {
        infoLog("Setting all pins low");
        for (Integer pinAddress : provisionedPins.keySet()) {
            provisionedPins.put(pinAddress, false);
        }
    }
}