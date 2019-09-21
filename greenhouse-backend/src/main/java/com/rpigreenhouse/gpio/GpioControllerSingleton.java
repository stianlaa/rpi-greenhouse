package com.rpigreenhouse.gpio;

public interface GpioControllerSingleton {
    public void setPin(OutputPin pin, Boolean state);

    public void setAllPinsLow();

    public boolean getPinState(InputPin pin);
}
