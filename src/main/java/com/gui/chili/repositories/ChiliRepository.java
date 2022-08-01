package com.gui.chili.repositories;

import com.gui.chili.entities.Chili;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChiliRepository extends MongoRepository<Chili, String> {
    Optional<Chili> findByName(@Param("name") String name);
}
