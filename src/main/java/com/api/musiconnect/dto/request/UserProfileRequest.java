package com.api.musiconnect.dto.request;

import java.time.LocalDate;
import java.util.List;

public record UserProfileRequest(
  String username,
  LocalDate birthdate,
  String bio,
  String ubication,
  String availability,
  String gender,
  List<String> instruments,
  List<String> musicalGenres,
  String country,
  String role
) {}
