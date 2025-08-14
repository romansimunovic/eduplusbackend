package com.edukatorplus.config;

import com.edukatorplus.service.JwtFilter;
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

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // npr. dev | prod
    @Value("${spring.profiles.active:}")
    private String activeProfile;

    // čita app.enableDevEndpoints ili direktno env ENABLE_DEV_ENDPOINTS
    @Value("${app.enableDevEndpoints:${ENABLE_DEV_ENDPOINTS:false}}")
    private boolean enableDevEndpoints;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                // public rute
                auth.requestMatchers(
                        "/api/auth/**",
                        "/api/ping",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll();

                // preflight
                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                // DEV util rute
                boolean devMode = "dev".equalsIgnoreCase(activeProfile);
                if (devMode || enableDevEndpoints) {
                    // samo ADMIN smije
                    auth.requestMatchers("/api/dev/**").hasRole("ADMIN");
                } else {
                    auth.requestMatchers("/api/dev/**").denyAll();
                }

                // sve ostalo treba valjan JWT
                auth.anyRequest().authenticated();
            })
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
