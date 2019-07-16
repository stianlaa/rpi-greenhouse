package com.rpigreenhouse.plants;

import java.time.LocalDateTime;

public class TomatoPlant extends Plant {
	boolean hasFruits = false;
	private static double growthSpeed = 0.5;

	public TomatoPlant(int waterLevel, LocalDateTime plantedDateTime) {
		super("Tomatoplant", waterLevel, growthSpeed, plantedDateTime);
	}

	public TomatoPlant() {
		super("Tomatoplant", 20, growthSpeed, LocalDateTime.now());
	}
}
