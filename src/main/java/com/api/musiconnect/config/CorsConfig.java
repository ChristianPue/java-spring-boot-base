package com.api.musiconnect.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

  @Value("${cors.allowed-origins}")
  private String allowedOrigins;

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of(allowedOrigins.split(",")));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")); // métodos permitidos
    config.setAllowedHeaders(List.of("*")); // cualquier cabecera (como Authorization)
    config.setAllowCredentials(true); // necesario si usas JWT o cookies
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config); // se aplica a todos los endpoints
    
    return new CorsFilter(source); // este filtro se activa automáticamente
  }
}
