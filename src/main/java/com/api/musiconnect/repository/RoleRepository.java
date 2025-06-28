package com.api.musiconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.musiconnect.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.name = :name")
  Boolean existsByName(@Param("name") String name);

  @Query("SELECT r FROM Role r WHERE r.name = :name")
  Optional<Role> findByName(@Param("name") String name);
}
