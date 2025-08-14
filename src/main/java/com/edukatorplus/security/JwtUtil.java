package com.edukatorplus.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

    // .env / Render env: JWT_SECRET = base64-encoded 256-bit (ili više) key
    private final Key key;
    private final String issuer;
    private final long expirationMillis;

    public JwtUtil(
            @Value("${jwt.secret}") String base64Secret,
            @Value("${jwt.issuer:eduplus}") String issuer,
            @Value("${jwt.expiration-ms:900000}") long expirationMillis // default 15 min
    ) {
        // ako ti je secret raw (ne base64), zamijeni Decoders.BASE64.decode(...) s base64Secret.getBytes()
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.issuer = issuer;
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(String email, String role) {
        final Date now = new Date();
        final Date exp = new Date(now.getTime() + expirationMillis);

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
                .setAllowedClockSkewSeconds(60) // tolerancija satova
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
        try {
            extractClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            // po potrebi: logika za “istekao” vs “nevaljan”
            return false;
        } catch (JwtException ex) {
            return false;
        }
    }

    // helper (ako treba negdje)
    public long getExpirationMillis() {
        return expirationMillis;
    }
}
