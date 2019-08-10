package com.rpigreenhouse.gpio;

public interface GpioControllerSingleton {
    public void setPin(int address, Boolean state);
    public void setAllPinsLow();
    public Boolean getPinState(int address);
}
