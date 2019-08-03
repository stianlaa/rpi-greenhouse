package com.rpigreenhouse.gpio;

public interface GpioControllerSingleton {
    public void setPin(int address, Boolean state);
    public Boolean getPinState(int address);
}
