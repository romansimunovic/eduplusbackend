package com.edukatorplus.config;

import com.edukatorplus.service.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> {
                // javno dostupni endpointi
                auth.requestMatchers(
                        "/api/auth/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                ).permitAll();

                // dev seed endpoint: u 'dev' profilu dopušten ADMIN-u, u ostalim profilima zabranjen
                if ("dev".equalsIgnoreCase(activeProfile)) {
                    auth.requestMatchers("/api/dev/**").hasRole("ADMIN");
                } else {
                    auth.requestMatchers("/api/dev/**").denyAll();
                }

                // sve ostalo zahtijeva autentikaciju
                auth.anyRequest().authenticated();
            })
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
