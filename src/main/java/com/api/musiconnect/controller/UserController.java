package com.api.musiconnect.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.musiconnect.dto.request.LoginRequest;
import com.api.musiconnect.dto.request.SignupRequest;
import com.api.musiconnect.dto.request.UserProfileRequest;
import com.api.musiconnect.dto.response.LoginResponse;
import com.api.musiconnect.dto.response.UserProfileResponse;
import com.api.musiconnect.model.enums.UserAvailability;
import com.api.musiconnect.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/signup")
  public void signup(@Valid @RequestBody SignupRequest signupRequest) {
    userService.signup(signupRequest);
  }

  @PostMapping("/login")
  public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest);
  }

  @GetMapping("/profile/{username}")
  public UserProfileResponse getUserProfile(@PathVariable String username) {
    return userService.getUserProfile(username);
  }

  @PutMapping("/profile")
  public UserProfileResponse updateUserProfile(@Valid @RequestBody UserProfileRequest userProfileRequest) {
    return userService.updateUserProfile(userProfileRequest);
  }

  @PatchMapping("/availability/{id}")
  public UserProfileResponse updateAvailability(@PathVariable Long id, @RequestParam UserAvailability availability) {
    return userService.updateAvailability(id, availability);
  }

  @GetMapping("/profiles")
  public List<UserProfileResponse> getAllUserProfiles() {
    return userService.getAllUserProfiles();
  }
}
