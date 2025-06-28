package com.api.musiconnect.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest(
  @NotBlank(message = "El email no puede estar vacío.")
  @Email(message = "El email tiene que tener un formato válido.")
  String email,
  
  @NotBlank(message = "La contraseña no puede estar vacía.")
  @Size(min = 6, message = "La contraseña tiene que tener como mínimo 6 caracteres.")
  String password,
  
  @NotBlank(message = "El username no puede estar vacío.")
  @Size(min = 4, max = 6, message = "El username tiene que tener como mínimo entre 4 y 6 caracteres.")
  String username,
  
  @NotNull(message = "El roleId no puede ser nulo.")
  Long roleId,
  
  @NotNull(message = "El countryId no puede ser nulo.")
  Long countryId
) {}
