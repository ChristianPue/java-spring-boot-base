package com.api.musiconnect.model.entity;

import java.time.LocalDate;
import java.util.List;

import com.api.musiconnect.model.enums.UserAvailability;
import com.api.musiconnect.model.enums.UserGender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(unique = true, nullable = false)
  private String username;
  private LocalDate birthdate;
  
  @Column(columnDefinition = "TEXT")
  private String bio;
  private String ubication;
  
  @Enumerated(EnumType.STRING)
  private UserAvailability availability;
  
  @Enumerated(EnumType.STRING)
  private UserGender gender;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_instruments",
             joinColumns = @JoinColumn(name = "user_profile_id"),
             inverseJoinColumns = @JoinColumn(name = "instrument_id"))
  private List<Instrument> instruments;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_musical_genres",
             joinColumns = @JoinColumn(name = "user_profile_id"),
             inverseJoinColumns = @JoinColumn(name = "musical_genre_id"))
  private List<MusicalGenre> musicalGenres;
}