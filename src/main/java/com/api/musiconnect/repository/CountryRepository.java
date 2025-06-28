package com.api.musiconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.musiconnect.model.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
  @Query("SELECT COUNT(c) > 0 FROM Country c WHERE c.name = :name")
  Boolean existsByName(@Param("name") String name);

  @Query("SELECT c FROM Country c WHERE c.name = :name")
  Optional<Country> findByName(@Param("name") String name);
}
