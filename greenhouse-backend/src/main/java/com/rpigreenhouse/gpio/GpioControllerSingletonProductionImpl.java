package com.rpigreenhouse.gpio;

import com.pi4j.io.gpio.*;
import com.rpigreenhouse.exceptions.UnexpectedPinUseException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.rpigreenhouse.GreenhouseLogger.debugLog;
import static com.rpigreenhouse.GreenhouseLogger.errorLog;

@Component
@Profile("prod")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GpioControllerSingletonProductionImpl implements GpioControllerSingleton {

    private final static List<Integer> FUNCTIONAL_PINS = new ArrayList<>(Arrays.asList(0, 2)); // todo add more as needed
    private final GpioController gpio;
    private final Map<Integer, GpioPinDigitalOutput> provisionedPins = new HashMap<>();

    public GpioControllerSingletonProductionImpl() {
        this.gpio = GpioFactory.getInstance();
    }

    public void setPin(int address, Boolean state) {
        if (!FUNCTIONAL_PINS.contains(address)) {
            errorLog(String.format("Put the pin %d to state %b", address, state));
            throw new UnexpectedPinUseException(address);
        }

        if (!provisionedPins.containsKey(address)) {
            Pin pinToProvision = RaspiPin.getPinByAddress(address);
            provisionDigitalOutputPin(pinToProvision);
        }

        debugLog(String.format("Setting pin: %d state to %b", address, state));
        provisionedPins.get(address).setState(state);
    }

    private void provisionDigitalOutputPin(Pin pinToProvision) {
        final GpioPinDigitalOutput provisionedPin = gpio.provisionDigitalOutputPin(pinToProvision, pinToProvision.getName(), PinState.LOW);
        provisionedPin.setShutdownOptions(true, PinState.LOW);

        this.provisionedPins.put(pinToProvision.getAddress(), provisionedPin);
    }

    public Boolean getPinState(int address) {
        return false; // todo implemented when needed
    }

    public void setAllPinsLow() {
        for (Integer pinAddress : provisionedPins.keySet()) {
            provisionedPins.get(pinAddress).setState(false);
        }
    }
}