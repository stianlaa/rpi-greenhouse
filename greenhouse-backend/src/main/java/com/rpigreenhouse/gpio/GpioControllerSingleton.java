package com.rpigreenhouse.gpio;

public interface GpioControllerSingleton {
    public void setPin(OutputPin pin, Boolean state);

    public void setAllPinsLow();

    public Boolean getPinState(InputPin pin);
}
