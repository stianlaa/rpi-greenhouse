package com.rpigreenhouse.storage.plant;

import com.rpigreenhouse.plants.Plant;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "PLANT_TYPE_IMAGE_TABLE")
public class PlantTypeImageDAO {

    @Id
    @Column(unique = true)
    private String plantType;

    @Lob
    @Column(name = "PLANT_IMAGE", nullable = false, columnDefinition = "mediumblob")
    private byte[] plantTypeImage;

    public PlantTypeImageDAO(Plant plant) {
        this.plantType = plant.getPlantType();
        this.plantTypeImage = plant.getPlantTypeImage();
    }
}
