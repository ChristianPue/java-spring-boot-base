package com.api.musiconnect.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.musiconnect.dto.request.SignupRequest;
import com.api.musiconnect.dto.request.UserProfileRequest;
import com.api.musiconnect.dto.request.LoginRequest;
import com.api.musiconnect.dto.response.LoginResponse;
import com.api.musiconnect.dto.response.UserProfileResponse;
import com.api.musiconnect.exception.BusinessRuleException;
import com.api.musiconnect.exception.DuplicatedResourceException;
import com.api.musiconnect.exception.ResourceNotFoundException;
import com.api.musiconnect.mapper.UserMapper;
import com.api.musiconnect.model.entity.Country;
import com.api.musiconnect.model.entity.Instrument;
import com.api.musiconnect.model.entity.MusicalGenre;
import com.api.musiconnect.model.entity.Role;
import com.api.musiconnect.model.entity.User;
import com.api.musiconnect.model.enums.UserAvailability;
import com.api.musiconnect.model.enums.UserGender;
import com.api.musiconnect.repository.CountryRepository;
import com.api.musiconnect.repository.InstrumentRepository;
import com.api.musiconnect.repository.MusicalGenreRepository;
import com.api.musiconnect.repository.RoleRepository;
import com.api.musiconnect.repository.UserRepository; 
import com.api.musiconnect.security.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  // REPOSITORIOS
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final CountryRepository countryRepository;
  private final InstrumentRepository instrumentRepository;
  private final MusicalGenreRepository musicGenreRepository;

  // SECURITY
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  @Transactional
  public void signup(SignupRequest request) {
    // EXCEPTIONS
    // Email duplicado
    if (userRepository.existsByEmail(request.email())) {
      throw new DuplicatedResourceException("Ya existe un usuario con dicho email.");
    }

    // Username duplicado
    if (userRepository.existsByUsername(request.username())) {
      throw new DuplicatedResourceException("Ya existe un usuario con dicho username.");
    }
    
    // Role no encontrado
    Role role = roleRepository.findById(request.roleId())
      .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado."));
    // Se eligió el Role administrador
    if (request.roleId() == 1) {
      throw new BusinessRuleException("No puedes registrarte como administrador.");
    }
    
    // Country no encontrado
    Country country = countryRepository.findById(request.countryId())
      .orElseThrow(() -> new ResourceNotFoundException("Country no encontrado."));
    
    User user = UserMapper.toEntity(request, passwordEncoder.encode(request.password()), role, country);
    
    userRepository.save(user);
  }

  @Transactional
  public LoginResponse login(LoginRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.username(), request.password())
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
    
    // EXCEPTIONS
    // Username no existente
    User user = userRepository.findByUsername(userDetails.getUsername())
      .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con dicho username."));
    
    // Generación del token
    String token = jwtUtil.generateToken(userDetails);

    return UserMapper.toDto(user, token);
  }

  @Transactional
  public UserProfileResponse getUserProfile(String username) {
    // EXCEPTIONS
    // Username no existe
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con dicho username."));
    
    return UserMapper.toDto(user);
  }

  @Transactional
  public UserProfileResponse updateUserProfile(UserProfileRequest request) {
    // EXCEPTIONS
    // Username no existe
    User user = userRepository.findByUsername(request.username())
      .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con dicho username."));

    // Username ya existe (esta en uso)
    if (request.username() != null && !request.username().isBlank()) {
      if (userRepository.existsByUsername(request.username())) {
        throw new BusinessRuleException("El username ya existe.");
      }

      user.getUserProfile().setUsername(request.username());
    }

    // Birthdate no es válido (menor de edad)
    if (request.birthdate() != null) {
      if (request.birthdate().isAfter(LocalDate.now().minusYears(18))) {
        throw new BusinessRuleException("El usuario debe ser mayor de edad.");
      }

      user.getUserProfile().setBirthdate(request.birthdate());
    }

    // Bio es nulo o está en blanco
    if (request.bio() != null && !request.bio().isBlank()) {
      user.getUserProfile().setBio(request.bio());
    }

    // Ubication es nulo o está en blanco
    if (request.ubication() != null && !request.ubication().isBlank()) {
      user.getUserProfile().setUbication(request.ubication());
    }

    // UserAvailability no es válido
    if (request.availability() != null) {
      user.getUserProfile().setAvailability(UserAvailability.valueOf(request.availability()));
    }

    // UserGender no es válido
    if (request.gender() != null) {
      user.getUserProfile().setGender(UserGender.valueOf(request.gender()));
    }

    // Algún Instrument no válido
    if (request.instruments() != null && !request.instruments().isEmpty()) {
      List<Instrument> instruments = request.instruments().stream()
        .map(instrumentName -> instrumentRepository.findByName(instrumentName)
          .orElseThrow(() -> new ResourceNotFoundException("No existe un instrumento con dicho nombre.")))
        .toList();

      user.getUserProfile().setInstruments(instruments);
    }

    // Algún MusicalGenre no válido
    if (request.musicalGenres() != null && !request.musicalGenres().isEmpty()) {
      List<MusicalGenre> musicalGenres = request.musicalGenres().stream()
        .map(musicalGenreName -> musicGenreRepository.findByName(musicalGenreName)
          .orElseThrow(() -> new ResourceNotFoundException("No existe un género musical con dicho nombre.")))
        .toList();

      user.getUserProfile().setMusicalGenres(musicalGenres);
    }

    // Country no es válido
    if (request.country() != null && !request.country().isBlank()) {
      Country country = countryRepository.findByName(request.country())
        .orElseThrow(() -> new ResourceNotFoundException("No existe un país con dicho nombre."));

      user.getUserProfile().setCountry(country);
    }

    // Role no es válido
    if (request.role() != null && !request.role().isBlank()) {
      Role role = roleRepository.findByName(request.role())
        .orElseThrow(() -> new ResourceNotFoundException("No existe un rol con dicho nombre."));

      user.setRole(role);
    }

    userRepository.save(user);

    return UserMapper.toDto(user);
  }

  @Transactional
  public UserProfileResponse updateAvailability(Long id, UserAvailability availability) {
    // Validar existencia del usuario
    User user = userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con dicho id."));

    // Asignar la disponibilidad directamente
    user.getUserProfile().setAvailability(availability);

    // Guardar los cambios en dicho usuario
    userRepository.save(user);

    // Retornar DTO del perfil actualizado
    return UserMapper.toDto(user);
  }

  @Transactional
  public List<UserProfileResponse> getAllUserProfiles() {
    List<User> users = userRepository.findAll();

    return users.stream()
      .map(UserMapper::toDto)
      .toList();
  }
}