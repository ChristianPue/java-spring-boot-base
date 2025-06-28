package com.api.musiconnect.dto.response;

import java.time.LocalDate;
import java.util.List;

public record UserProfileResponse(
  Long id,
  String username,
  LocalDate birthdate,
  String bio,
  String ubication,
  String availability,
  String gender,
  List<String> instruments,
  List<String> musicalGenres,
  String country
) {}
