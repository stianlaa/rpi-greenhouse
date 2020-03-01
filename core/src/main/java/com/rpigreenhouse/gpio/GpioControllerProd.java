package com.rpigreenhouse.gpio;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

import static com.rpigreenhouse.GreenhouseLogger.debugLog;
import static com.rpigreenhouse.GreenhouseLogger.infoLog;


@Component
@Profile("prod")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GpioControllerProd implements GpioController {

    private final com.pi4j.io.gpio.GpioController gpio;
    private final Map<Integer, GpioPinDigitalOutput> provisionedOutPins = new HashMap<>();
    private final Map<Integer, GpioPinDigitalInput> provisionedInPins = new HashMap<>();

    public GpioControllerProd() {
        this.gpio = GpioFactory.getInstance();
    }

    @PreDestroy
    public void destroy() {
        setAllPinsLow();
    }

    @Override
    public void setPin(OutputPin pin, Boolean state) {
        Integer address = pin.getPinAddress();
        if (!provisionedOutPins.containsKey(address)) {
            Pin pinToProvision = RaspiPin.getPinByAddress(address);
            provisionDigitalOutputPin(pinToProvision);
        }

        debugLog(String.format("Setting pin: %d state to %b", address, state));
        provisionedOutPins.get(address).setState(state);
    }

    @Override
    public boolean getPinState(InputPin pin) {
        Integer address = pin.getPinAddress();
        if (!provisionedInPins.containsKey(address)) {
            Pin pinToProvision = RaspiPin.getPinByAddress(address);
            provisionDigitalInputPin(pinToProvision);
        }
        return provisionedInPins.get(address).getState().isHigh();
    }

    @Override
    public void setAllPinsLow() {
        infoLog("Setting all pins low");
        for (Integer pinAddress : provisionedOutPins.keySet()) {
            provisionedOutPins.get(pinAddress).setState(false);
        }
    }

    private void provisionDigitalOutputPin(Pin pinToProvision) {
        final GpioPinDigitalOutput provisionedPin = gpio.provisionDigitalOutputPin(pinToProvision, pinToProvision.getName(), PinState.LOW);
        provisionedPin.setShutdownOptions(true, PinState.LOW);

        this.provisionedOutPins.put(pinToProvision.getAddress(), provisionedPin);
    }

    private void provisionDigitalInputPin(Pin pinToProvision) {
        final GpioPinDigitalInput provisionedPin = gpio.provisionDigitalInputPin(pinToProvision);
        this.provisionedInPins.put(pinToProvision.getAddress(), provisionedPin);
    }
}