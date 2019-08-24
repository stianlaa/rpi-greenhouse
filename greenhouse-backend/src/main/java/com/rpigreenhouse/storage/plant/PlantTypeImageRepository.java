package com.rpigreenhouse.storage.plant;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlantTypeImageRepository extends CrudRepository<PlantTypeImageDAO, Integer> {
    Optional<PlantTypeImageDAO> findByPlantType(String plantType);
}
