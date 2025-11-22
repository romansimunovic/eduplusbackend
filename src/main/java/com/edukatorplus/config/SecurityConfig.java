package com.edukatorplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    // SecurityFilterChain za Spring Boot 3.x
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // Omogući CORS
            .csrf(csrf -> csrf.disable())   // Disable CSRF za API
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()   // Sve dozvoli za testing/dev 
            );
        return http.build();
    }

    // PasswordEncoder bean za UserService
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Najsigurniji način: explicitno CORS filter s dopuštenim originima
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "https://eduplusfrontend-j21ehdb93-romansimunovics-projects.vercel.app",
            "https://eduplusfrontend.vercel.app",
            "https://eduplusfrontend-9xbwcxyx4-romansimunovics-projects.vercel.app",
            "http://localhost:3000"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);     // Omogući header za JWT/cookie
        config.setExposedHeaders(List.of("Authorization")); // Omogući response za auth

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
