package com.api.musiconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.musiconnect.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.userProfile.username = :username")
  Boolean existsByUsername(@Param("username") String username);

  @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
  Boolean existsByEmail(@Param("email") String email);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findByEmail(@Param("email") String email);

  @Query("SELECT u FROM User u WHERE u.userProfile.username = :username")
  Optional<User> findByUsername(@Param("username") String username);
}
