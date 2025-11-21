package com.edukatorplus.util;

import com.edukatorplus.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Component
public class JWTUtil {
    private static final String SECRET = "SECRET_KEY_12345678901234567890"; // barem 32 znaka!
    private static final long EXPIRATION = 3600000; // 1h

    // Pretvori secret u Key
    private Key getSigningKey() {
        byte[] keyBytes = SECRET.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("email", user.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Dodatno: metoda za dohvat emaila/role iz tokena
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }
}
