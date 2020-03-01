package com.rpigreenhouse.storage.plant;

import com.rpigreenhouse.dao.PlantDAO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlantRepository extends CrudRepository<PlantDAO, Integer> {

    Optional<PlantDAO> findById(Integer id);

    Iterable<PlantDAO> findAll();

    <S extends PlantDAO> S save(S plant);
}
