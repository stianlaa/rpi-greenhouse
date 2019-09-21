package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.managers.sensor.DispenserLevelSensor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.rpigreenhouse.GreenhouseLogger.errorLog;

@Component
public class Dispenser {

    private static final Double DISPENSER_TARGET_VOLUME = 1.0;
    private static final Double HEIGHT = 0.207;
    private static final Double WIDTH = 0.105;
    private static final Double DEPTH = 0.075;

    private static final Double MAX_VOLUME = 1.6;
    private static final Double MIN_VOLUME = 0.0;

    private PumpRegulator pumpRegulator;
    private ValveRegulator valveRegulator;
    private DispenserLevelSensor dispenserLevelSensor;



    public Dispenser(PumpRegulator pumpRegulator,
                     ValveRegulator valveRegulator,
                     DispenserLevelSensor dispenserLevelSensor) {
        this.pumpRegulator = pumpRegulator;
        this.valveRegulator = valveRegulator;
        this.dispenserLevelSensor = dispenserLevelSensor;
    }

    public void giveTrayWater(Integer trayId, Integer waterVolumeMl) {
        try {

            fillToTargetVolume();

            dispenseVolumeToTray(trayId, waterVolumeMl);

            valveRegulator.closeAllValves();

        } catch (InterruptedException e) {
            errorLog("Interrupt was registered");
        }
    }

    private void fillToTargetVolume() throws InterruptedException {
        pumpRegulator.setPumpMode(true);
        while (dispenserLevelSensor.getStateEstimate() < DISPENSER_TARGET_VOLUME) {
            TimeUnit.MICROSECONDS.sleep(dispenserLevelSensor.getRefreshInterval());
        }
        pumpRegulator.setPumpMode(false);
    }

    private void dispenseVolumeToTray(Integer trayId, Integer waterVolumeMl) throws InterruptedException {
        Double startingVolume = calculateVolumeFromSensorDistance(dispenserLevelSensor.getStateEstimate());
        valveRegulator.directValveToTray(trayId);
        while (startingVolume - calculateVolumeFromSensorDistance(dispenserLevelSensor.getStateEstimate()) < waterVolumeMl) {
            TimeUnit.MILLISECONDS.sleep(50L);
        }
    }

    private Double calculateVolumeFromSensorDistance(Double distanceFromTop) {
        Double volumeEstimate = ((HEIGHT - distanceFromTop)*WIDTH*DEPTH) * 1000; // volume in m^3 to liters
        if (volumeEstimate > MAX_VOLUME) { return MAX_VOLUME;}
        if (volumeEstimate < MIN_VOLUME) { return MIN_VOLUME;}
        return volumeEstimate;
    }
}
