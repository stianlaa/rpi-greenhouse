package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.managers.sensor.DispenserVolumeSensor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Dispenser {

    private static final Double DISPENSER_TARGET_VOLUME = 1.0;
    private PumpRegulator pumpRegulator;
    private ValveRegulator valveRegulator;
    private DispenserVolumeSensor dispenserVolumeSensor;

    public Dispenser(PumpRegulator pumpRegulator,
                     ValveRegulator valveRegulator,
                     DispenserVolumeSensor dispenserVolumeSensor) {
        this.pumpRegulator = pumpRegulator;
        this.valveRegulator = valveRegulator;
        this.dispenserVolumeSensor = dispenserVolumeSensor;
    }

    public void giveTrayWater(Integer trayId, Integer waterVolumeMl) {
        try {

            fillToVolume(DISPENSER_TARGET_VOLUME);

            dispenseVolumeToTray(trayId, waterVolumeMl);

            valveRegulator.closeAllValves();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fillToVolume(Double targetVolume) throws InterruptedException {
        pumpRegulator.setPumpMode(true);
        while (dispenserVolumeSensor.getStateEstimate() < targetVolume) {
            TimeUnit.MILLISECONDS.sleep(50L);
        }
        pumpRegulator.setPumpMode(false);
    }

    private void dispenseVolumeToTray(Integer trayId, Integer waterVolumeMl) throws InterruptedException {
        Double startingVolume = dispenserVolumeSensor.getStateEstimate();
        valveRegulator.directValveToTray(trayId);
        while (startingVolume - dispenserVolumeSensor.getStateEstimate() < waterVolumeMl) {
            TimeUnit.MILLISECONDS.sleep(50L);
        }
    }
}
