package com.edukatorplus.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

    private final String issuer;
    private final Duration expiration;
    private final Key key;

    // Konfigurabilno preko env-a/properties:
    // jwt.secret, jwt.issuer, jwt.expDays
    public JwtUtil(
            @Value("${jwt.secret:tajna_lozinka_tajna_lozinka_tajna_lozinka_123}") String secret,
            @Value("${jwt.issuer:eduplus}") String issuer,
            @Value("${jwt.expDays:1}") long expDays
    ) {
        this.issuer = issuer;
        this.expiration = Duration.ofDays(expDays);
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration.toMillis());

        return Jwts.builder()
                .setSubject(email)
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
                .setAllowedClockSkewSeconds(60) // 60s tolerancije
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        Object role = extractClaims(token).get("role");
        return role != null ? role.toString() : null;
    }

    public boolean isTokenValid(String token) {
        if (token == null || token.isBlank()) return false;
        try {
            extractClaims(token);
            return true;
        } catch (JwtException ex) { // pokriva i ExpiredJwtException
            return false;
        }
    }

    public long getExpirationMillis() {
        return expiration.toMillis();
    }
}
