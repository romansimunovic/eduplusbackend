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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    // npr. "dev" lokalno / dev grana; na Renderu ostavi prazno ili "prod"
    @Value("${spring.profiles.active:}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // stateless JWT
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            // ⚠️ koristimo authorizeRequests + antMatchers (kompatibilno s Spring Security 5.7)
            .authorizeRequests(auth -> {
                // javno: auth + swagger
                auth.antMatchers(
                        "/api/auth/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                ).permitAll();

                // DEV alati
                if ("dev".equalsIgnoreCase(activeProfile)) {
                    // u dev profilu dozvoli, ali samo ADMIN-u
                    auth.antMatchers("/api/dev/**").hasRole("ADMIN");
                } else {
                    // u produkciji (main/prod) — zabrani
                    auth.antMatchers("/api/dev/**").denyAll();
                }

                // MAIN/PROD: dopusti JAVNE GET-ove da stranice rade bez logina
                if (!"dev".equalsIgnoreCase(activeProfile)) {
                    auth.antMatchers(HttpMethod.GET, "/api/radionice/**").permitAll();
                    auth.antMatchers(HttpMethod.GET, "/api/polaznici/**").permitAll();
                    // koristi '/api/prisustva' ili '/api/prisustva/view' ovisno o tvom kontroleru
                    auth.antMatchers(HttpMethod.GET, "/api/prisustva/**").permitAll();
                }

                // sve ostalo traži token
                auth.anyRequest().authenticated();
            })

            // JWT filter prije standardnog auth filtera
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // po potrebi promijeni "strength" (default 10)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}
