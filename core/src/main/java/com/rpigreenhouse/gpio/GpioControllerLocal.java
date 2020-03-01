package com.rpigreenhouse.gpio;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static com.rpigreenhouse.GreenhouseLogger.infoLog;

@Component
@Profile("local")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GpioControllerLocal implements GpioController {

    private final Map<Integer, Boolean> provisionedPins = new HashMap<>();
    private final Map<Integer, Boolean> simulatedPins = new HashMap<>();

    @PreDestroy
    public void destroy() {
        setAllPinsLow();
    }

    public GpioControllerLocal() {
        IntStream.range(0, 40).forEach(pinIndex -> simulatedPins.put(pinIndex, false));
    }

    @Override
    public void setPin(OutputPin pin, Boolean state) {
        Integer address = pin.getPinAddress();
        provisionedPins.put(address, state);
    }

    @Override
    public boolean getPinState(InputPin pin) {
        return simulatedPins.get(pin.getPinAddress());
    }

    @Override
    public void setAllPinsLow() {
        infoLog("Setting all pins low");
        for (Integer pinAddress : provisionedPins.keySet()) {
            provisionedPins.put(pinAddress, false);
        }
    }
}