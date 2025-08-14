package com.edukatorplus.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "tajna_lozinka_tajna_lozinka_tajna_lozinka_123";
    private static final String ISSUER = "eduplus";
    private static final Duration EXPIRATION = Duration.ofDays(1);

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email, String role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + EXPIRATION.toMillis());

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .requireIssuer(ISSUER)
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
        try {
            extractClaims(token);
            return true;
        } catch (ExpiredJwtException | JwtException ex) {
            return false;
        }
    }

    public long getExpirationMillis() { return EXPIRATION.toMillis(); }
}
