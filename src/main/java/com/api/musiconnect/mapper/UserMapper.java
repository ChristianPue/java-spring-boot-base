package com.api.musiconnect.mapper;

import java.time.LocalDateTime;

import com.api.musiconnect.dto.request.SignupRequest;
import com.api.musiconnect.dto.response.LoginResponse;
import com.api.musiconnect.dto.response.UserProfileResponse;
import com.api.musiconnect.model.entity.Country;
import com.api.musiconnect.model.entity.Instrument;
import com.api.musiconnect.model.entity.MusicalGenre;
import com.api.musiconnect.model.entity.Role;
import com.api.musiconnect.model.entity.User;
import com.api.musiconnect.model.entity.UserProfile;

public class UserMapper {
  public static User toEntity(SignupRequest request, String encodedPassword, Role role, Country country) {
    UserProfile userProfile = UserProfile.builder()
      .username(request.username())
      .country(country)
      .build();

    return User.builder()
      .email(request.email())
      .password(encodedPassword)
      .role(role)
      .userProfile(userProfile)
      .createdAt(LocalDateTime.now())
      .build();
  }

  public static LoginResponse toDto(User user, String token) {
    return new LoginResponse(
      user.getId(),
      user.getUserProfile().getUsername(),
      token
    );
  }

  public static UserProfileResponse toDto(User user) {
    return new UserProfileResponse(
      user.getId(),
      user.getUserProfile().getUsername(),
      user.getUserProfile().getBirthdate(),
      user.getUserProfile().getBio(),
      user.getUserProfile().getUbication(),
      user.getUserProfile().getAvailability().toString(),
      user.getUserProfile().getGender().toString(),
      user.getUserProfile().getInstruments().stream().map(Instrument::getName).toList(),
      user.getUserProfile().getMusicalGenres().stream().map(MusicalGenre::getName).toList(),
      user.getUserProfile().getCountry().getName()
    );
  }
}
