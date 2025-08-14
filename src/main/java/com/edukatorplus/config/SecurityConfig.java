package com.edukatorplus.config;

import com.edukatorplus.service.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    // Ako je aktivan "dev" profil, dopuštamo /api/dev/** ADMIN-ima; inače ga blokiramo
    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CORS prema CorsConfig bean-u
            .cors(Customizer.withDefaults())
            // stateless JWT
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // autorizacija
            .authorizeHttpRequests(auth -> {
                // javno dostupni endpointi
                auth.requestMatchers(
                        "/api/auth/**",
                        "/api/ping",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                ).permitAll();

                // dev-seed endpointi
                if ("dev".equalsIgnoreCase(activeProfile)) {
                    auth.requestMatchers("/api/dev/**").hasRole("ADMIN");
                } else {
                    auth.requestMatchers("/api/dev/**").denyAll();
                }

                // sve ostalo traži autentikaciju
                auth.anyRequest().authenticated();
            })
            // naš JWT filter ide prije UsernamePasswordAuthenticationFilter-a
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // po potrebi promijeni strength (default 10)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}