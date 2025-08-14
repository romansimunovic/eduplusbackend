package com.edukatorplus.config;

import com.edukatorplus.service.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CORS (ako imaš globalni CorsFilter, i ovo je ok)
            .cors(withDefaults())
            // stateless JWT -> bez CSRF-a i bez sessiona
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> {
                // Otvoreni endpointi (auth i health/ping)
                auth.requestMatchers(
                        "/api/auth/**",
                        "/api/ping"
                ).permitAll();

                // Preflight
                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                // Swagger / OpenAPI
                auth.requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll();

                // DEV seed endpoint:
                // Ako želiš strogo samo u dev profilu – otvori ADMIN-u, a u prod profilu zabrani:
                // if ("dev".equalsIgnoreCase(activeProfile)) {
                //     auth.requestMatchers("/api/dev/**").hasRole("ADMIN");
                // } else {
                //     auth.requestMatchers("/api/dev/**").denyAll();
                // }

                // Ako želiš dopustiti ADMIN-u i u produkciji, koristi ovo:
                auth.requestMatchers("/api/dev/**").hasRole("ADMIN");

                // Sve ostalo traži autenticiran JWT
                auth.anyRequest().authenticated();
            })

            // Uključi naš JWT filter prije UsernamePasswordAuthenticationFilter-a
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // default strength = 10
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}