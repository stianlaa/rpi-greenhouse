package com.rpigreenhouse.managers.watering;

import com.rpigreenhouse.greenhouse.Tray;
import com.rpigreenhouse.plants.Plant;
import com.rpigreenhouse.storage.GreenhouseStorage;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.rpigreenhouse.GreenhouseLogger.debugLog;
import static com.rpigreenhouse.GreenhouseLogger.warnLog;

@Component
public class WaterManager {

    private Dispenser dispenser;
    private GreenhouseStorage greenhouseStorage;

    private ScheduledFuture<?> wateringSchedule = null;
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private Map<Integer, Integer> trayWaterOrders = new TreeMap<>();

    @Getter
    private Boolean busyStatus = false;

    public WaterManager(GreenhouseStorage greenhouseStorage,
                        Dispenser dispenser) {
        this.greenhouseStorage = greenhouseStorage;
        this.dispenser = dispenser;
    }

    public void giveTrayWater(Integer trayId, Integer waterVolumeMl) {
        dispenser.giveTrayWater(trayId, waterVolumeMl);
    }

    public Long startWaterCheckingSchedule(LocalDateTime firstWatering, Long wateringInterval) {
        if (wateringSchedule == null) {
            wateringSchedule = service.scheduleAtFixedRate(this::waterAllPlants,
                    findSecondsToFirstWatering(firstWatering),
                    wateringInterval,
                    TimeUnit.SECONDS);
        }
        debugLog("The watering schedule was already started");
        return wateringSchedule.getDelay(TimeUnit.SECONDS);
    }

    public void stopWaterCheckingSchedule() {
        if (wateringSchedule == null) {
            warnLog("Tried to cancel watering schedule, but no schedule existed");
        } else {
            wateringSchedule.cancel(true);
        }
    }

    public LocalDateTime getNextWaterTime() {
        if (wateringSchedule == null) return null;
        return LocalDateTime.now().plusSeconds(wateringSchedule.getDelay(TimeUnit.SECONDS));
    }

    private Long findSecondsToFirstWatering(LocalDateTime startUpMoment) {
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), startUpMoment);
    }

    private void waterAllPlants() {
        debugLog("Watering all plants");
        busyStatus = true;

        for (Tray tray : greenhouseStorage.getTraysWithPlants()) {
            Integer waterForTray = 0;
            for (Plant plant : tray.getPlants()) {
                Integer plantWaterNeed = calculatePlantWaterNeed(plant, LocalDate.now());
                debugLog(String.format("Plant: %s requires %d ml water", plant.getPlantId(), plantWaterNeed));

                waterForTray = waterForTray + plantWaterNeed;
            }
            trayWaterOrders.put(tray.getTrayId(), waterForTray);
        }

        processWaterOrders();
        busyStatus = false;
    }

    private void processWaterOrders() {
        for (Integer trayId : trayWaterOrders.keySet()) {
            debugLog(String.format("Watering tray: %s, with waterVolume: %d ml", trayId, trayWaterOrders.get(trayId)));
            dispenser.giveTrayWater(trayId, trayWaterOrders.get(trayId));
        }
    }

    private Integer calculatePlantWaterNeed(Plant plant, LocalDate atDate) {
        if (atDate.isAfter(plant.getExpectedHarvestDate())) {
            return plant.getMatureWaterNeed();
        } else if (atDate.isBefore(plant.getPlantedDateTime().toLocalDate())) {
            return plant.getSeedWaterNeed();
        }

        Float daysPassed = (float) ChronoUnit.DAYS.between(plant.getPlantedDateTime().toLocalDate(), atDate);
        Float matureDuration = (float) ChronoUnit.DAYS.between(plant.getPlantedDateTime().toLocalDate(), plant.getExpectedHarvestDate());
        return Math.round(plant.getSeedWaterNeed() + (plant.getMatureWaterNeed() - plant.getSeedWaterNeed()) * (daysPassed / matureDuration));
    }
}
