package com.api.musiconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.musiconnect.model.entity.MusicalGenre;

public interface MusicalGenreRepository extends JpaRepository<MusicalGenre, Long> {
  @Query("SELECT COUNT(m) > 0 FROM MusicalGenre m WHERE m.name = :name")
  Boolean existsByName(@Param("name") String name);

  @Query("SELECT m FROM MusicalGenre m WHERE m.name = :name")
  Optional<MusicalGenre> findByName(@Param("name") String name);
}
