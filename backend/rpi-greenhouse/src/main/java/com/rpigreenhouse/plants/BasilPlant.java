package com.rpigreenhouse.plants;

import java.time.LocalDateTime;

public class BasilPlant extends Plant {
	boolean hasFruits = false;
	private static double growthSpeed = 0.2;

	public BasilPlant(int waterLevel, LocalDateTime plantedDateTime) {
		super("Basilplant", waterLevel, growthSpeed, plantedDateTime);
	}

	public BasilPlant() {
		super("Basilplant", 20, growthSpeed, LocalDateTime.now());
	}

}
