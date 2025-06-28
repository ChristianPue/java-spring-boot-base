package com.api.musiconnect.dto.response;

public record LoginResponse(
  Long id,
  String username,
  String token
) {}
