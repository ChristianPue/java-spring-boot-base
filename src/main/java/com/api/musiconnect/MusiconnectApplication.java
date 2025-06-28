package com.api.musiconnect;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.api.musiconnect.model.entity.Country;
import com.api.musiconnect.model.entity.Instrument;
import com.api.musiconnect.model.entity.MusicalGenre;
import com.api.musiconnect.model.entity.Role;
import com.api.musiconnect.repository.RoleRepository;
import com.api.musiconnect.repository.CountryRepository;
import com.api.musiconnect.repository.InstrumentRepository;
import com.api.musiconnect.repository.MusicalGenreRepository;

@SpringBootApplication
public class MusiconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusiconnectApplication.class, args);
	}

	// Inicializando valores para Role
	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.count() == 0) {
				List<String> predefinedRoles = List.of("ADMIN", "ARTIST", "PRODUCER");

				for (String roleName : predefinedRoles) {
					if (!roleRepository.existsByName(roleName)) {
						Role role = Role.builder()
							.name(roleName)
							.build();
						roleRepository.save(role);
					}
				}
			}
		};
	}

	// Inicializando valores para MusicalGenre
	@Bean
	CommandLineRunner initMusicalGenres(MusicalGenreRepository musicalGenreRepository) {
		return args -> {
			if (musicalGenreRepository.count() == 0) {
				List<String> predefinedMusicalGenres = List.of("ROCK", "POP", "JAZZ", "CLASSICAL", "HIP_HOP", "REGGAETEON", 
				"BLUES", "ELECTRONIC", "FOLK", "LATIN", "COUNTRY", "METAL", "RNB", "SOUL", "PUNK", "DISCO", "FUNK", "SALSA", "CUMBIA",
    		"BACHATA", "K_POP", "OPERA", "SOUNDTRACK", "SAYA", "CAPORAL", "INDIE");

				for (String musicalGenreName : predefinedMusicalGenres) {
					if (!musicalGenreRepository.existsByName(musicalGenreName)) {
						MusicalGenre musicalGenre = MusicalGenre.builder()
							.name(musicalGenreName)
							.build();
						musicalGenreRepository.save(musicalGenre);
					}
				}
			}
		};
	}

	// Inicializando valores para Instrument
	@Bean
	CommandLineRunner initInstruments(InstrumentRepository instrumentRepository) {
		return args -> {
			if (instrumentRepository.count() == 0) {
				List<String> predefinedInstruments = List.of("DRUMS", "GUITAR", "FLUTE", "PIANO", "TRUMPET", "TROMBONE", "SAXOPHONE", "ACOUSTIC_GUITAR", "ELECTRIC_GUITAR", "ZAMPOÑA");

				for (String instrumentName : predefinedInstruments) {
					if (!instrumentRepository.existsByName(instrumentName)) {
						Instrument instrument = Instrument.builder()
							.name(instrumentName)
							.build();
						instrumentRepository.save(instrument);
					}
				}
			}
		};
	}

	// Inicializar valores para Country
	@Bean
	CommandLineRunner initCountries(CountryRepository countryRepository) {
		return args -> {
			if (countryRepository.count() == 0) {
				List<String> predefinedCountries = List.of("EEUU", "CHINA", "BRAZIL", "JAPAN", "SPAIN", "PERU", "GERMANY");

				for (String countryName : predefinedCountries) {
					if (!countryRepository.existsByName(countryName)) {
						Country country = Country.builder()
							.name(countryName)
							.build();
						countryRepository.save(country);
					}
				}
			}
		};
	}
}
