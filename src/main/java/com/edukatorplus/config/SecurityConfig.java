package com.edukatorplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    // SecurityFilterChain za Spring Boot 3.x
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // Globalno uključi CORS
            .csrf(csrf -> csrf.disable())    // Isključi CSRF za REST API
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()    // Sve dozvoli (za testing/dev)
            );
        return http.build();
    }

    // PasswordEncoder bean za UserService
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Globalni CORS za cloud-client frontend
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("https://eduplusfrontend-j21ehdb93-romansimunovics-projects.vercel.app",
                                        "https://eduplusfrontend.vercel.app")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }
}
