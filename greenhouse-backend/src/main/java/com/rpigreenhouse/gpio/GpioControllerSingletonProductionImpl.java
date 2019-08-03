package com.rpigreenhouse.gpio;

import com.pi4j.io.gpio.*;
import com.rpigreenhouse.gpio.GpioControllerSingleton;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.rpigreenhouse.GreenhouseLogger.errorLog;

@Component
@Profile("prod")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GpioControllerSingletonProductionImpl implements GpioControllerSingleton {

    private final GpioController gpio;
    private final Map<Integer, GpioPinDigitalOutput> provisionedPins = new HashMap<>();

    public GpioControllerSingletonProductionImpl() {
        errorLog("Production GPIO BEAN WAS CREATED");
        this.gpio = GpioFactory.getInstance();
    }

    public void setPin(int address, Boolean state) {
        errorLog(String.format("Put the pin %d to state %b", address, state)); // todo replace with sensible level
        if (!provisionedPins.containsKey(address)) {
            Pin pinToProvision = RaspiPin.getPinByAddress(address);
            provisionDigitalOutputPin(pinToProvision);
        }
        provisionedPins.get(address).setState(state);
    }

    public void provisionDigitalOutputPin(Pin pinToProvision) {
        // provision gpio pin
        final GpioPinDigitalOutput provisionedPin = gpio.provisionDigitalOutputPin(pinToProvision, pinToProvision.getName(), PinState.LOW);
        provisionedPin.setShutdownOptions(true, PinState.LOW);

        this.provisionedPins.put(pinToProvision.getAddress(), provisionedPin);
    }

    public Boolean getPinState(int address) {
        return false; // todo fix
    }

}