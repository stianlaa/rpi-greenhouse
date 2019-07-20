package com.rpigreenhouse.managers;

import java.util.concurrent.TimeUnit;


public class PumpRegulator {
    private static final Integer WAIT_BEFORE_SWITCH = 1;

    public Boolean pumpVolume(Integer waterVolumeMl) throws InterruptedException {
        // todo set gpiopins to pump
        // errorLog("Started pumping");
        TimeUnit.SECONDS.sleep(calculatePumpTime(waterVolumeMl));
        // todo set gpiopins to stop pumping
        // errorLog("Stopped pumping");
        TimeUnit.SECONDS.sleep(WAIT_BEFORE_SWITCH);
        return true;
    }

    private Long calculatePumpTime(Integer waterVolumeMl) {
        return (long) (waterVolumeMl / 2); // todo adjust after measurement
    }
}
