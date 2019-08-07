package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.gpio.GpioControllerSingleton;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.rpigreenhouse.GreenhouseLogger.infoLog;

@Component
public class PumpRegulator {
    private static final Integer WAIT_BEFORE_SWITCH = 1;
    private static final Integer PUMP_PIN_ADDRESS = 0;
    private GpioControllerSingleton gpioControllerSingleton;

    public PumpRegulator(GpioControllerSingleton gpioControllerSingleton) {
        this.gpioControllerSingleton = gpioControllerSingleton;
    }

    public Boolean pumpVolume(Integer waterVolumeMl) throws InterruptedException {

        Long pumpForDuration = calculatePumpTime(waterVolumeMl);

        infoLog(String.format("Started pumping for %s seconds", pumpForDuration.toString()));
        gpioControllerSingleton.setPin(PUMP_PIN_ADDRESS, true);

        TimeUnit.SECONDS.sleep(calculatePumpTime(waterVolumeMl));

        gpioControllerSingleton.setPin(PUMP_PIN_ADDRESS, false);
        infoLog("Stopped pumping");

        TimeUnit.SECONDS.sleep(WAIT_BEFORE_SWITCH);
        return true;
    }

    private Long calculatePumpTime(Integer waterVolumeMl) {
        return (long) (waterVolumeMl / 2); // todo adjust after measurement
    }
}
