package com.edukatorplus.util;

import com.edukatorplus.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String SECRET; // čitamo iz env var

    private static final long EXPIRATION = 3600000; // 1h

    private Key getSigningKey() {
        byte[] keyBytes = SECRET.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(User user){
        if (SECRET == null) {
            System.err.println("JWT secret is null! Set JWT_SECRET env var.");
            throw new RuntimeException("JWT secret not set");
        }
        System.out.println("JWT secret length: " + SECRET.length());
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("email", user.getEmail());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
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
