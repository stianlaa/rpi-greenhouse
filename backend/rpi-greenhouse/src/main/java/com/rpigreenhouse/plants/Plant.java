package com.rpigreenhouse.plants;

import java.time.LocalDateTime;
import java.util.UUID;

public class Plant {
	private String plantId;
	private String name;
	private Integer waterLevel;
	private Double maturity;
	private Double growthSpeed;
	private LocalDateTime plantedDateTime;

	public Plant(String name, Integer waterLevel, Double growthSpeed, LocalDateTime plantedDateTime) {
		this.plantId = UUID.randomUUID().toString();
		this.waterLevel = waterLevel;
		this.name = name;
		this.maturity = 0.0;
		this.growthSpeed = growthSpeed;
		this.plantedDateTime = plantedDateTime;
	}

	public void waterPlant() {
		this.waterLevel = this.waterLevel + 10;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getPlantedLocalDateTime() {
		return plantedDateTime;
	}

	public String getPlantId() {
		return plantId;
	}

	public Integer getWaterLevel() {
		return waterLevel;
	}

	public void updateState() {
		if (waterLevel > 0) waterLevel--;
		if (maturity < 100) maturity += growthSpeed;
	}

	public String getStatus() {
		String printableMaturity = "";
		if (this.maturity != null) {
			printableMaturity = maturity.toString();
			if (printableMaturity.length() > 4) {
				printableMaturity = printableMaturity.substring(0, 4);
			}
		}
		return String.format("%s %s: waterlevel: %s, maturity: %s \t\t", name, plantId.substring(0, 4), waterLevel.toString(), printableMaturity);
	}
}
