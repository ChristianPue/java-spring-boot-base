package com.api.musiconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.musiconnect.model.entity.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
  @Query("SELECT COUNT(i) > 0 FROM Instrument i WHERE i.name = :name")
  Boolean existsByName(@Param("name") String name);

  @Query("SELECT i FROM Instrument i WHERE i.name = :name")
  Optional<Instrument> findByName(@Param("name") String name);
}
