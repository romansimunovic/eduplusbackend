package com.edukatorplus.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtil {

    private final String issuer;
    private final Duration expiration;
    private final Key key;

    public JwtUtil(
            @Value("${jwt.secret:}") String secretFromProps,
            @Value("${jwt.issuer:eduplus}") String issuer,
            @Value("${jwt.expDays:1}") long expDays,
            Environment env
    ) {
        this.issuer = issuer;
        this.expiration = Duration.ofDays(expDays);

        // Ako je secret prazan ili prekratak, ponašaj se ovisno o profilu:
        String active = env.getProperty("spring.profiles.active", "");
        boolean isDev = "dev".equalsIgnoreCase(active);

        String secret = (secretFromProps != null && !secretFromProps.isBlank())
                ? secretFromProps
                : (isDev ? "dev_super_secret_key_32bytes_min__ok__" : "");

        if (secret.isBlank() || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException(
                    "JWT secret is invalid (empty or < 32 bytes). Set property 'jwt.secret' to a strong value (>=32 chars)."
            );
        }

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration.toMillis());

        return Jwts.builder()
                .setSubject(Objects.requireNonNull(email, "email required"))
                .claim("role", role)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .requireIssuer(issuer)
                .setAllowedClockSkewSeconds(60)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) { return extractClaims(token).getSubject(); }

    public String extractRole(String token) {
        Object role = extractClaims(token).get("role");
        return role != null ? role.toString() : null;
    }

    public boolean isTokenValid(String token) {
        if (token == null || token.isBlank()) return false;
        try {
            extractClaims(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    public long getExpirationMillis() { return expiration.toMillis(); }
}
