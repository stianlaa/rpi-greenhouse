package com.rpigreenhouse.managers.sensor;

public interface Sensor {
    public void updateStateEstimate();

    public Float getStateEstimate();
}
