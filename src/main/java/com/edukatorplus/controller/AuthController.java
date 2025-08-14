package com.edukatorplus.controller;

import com.edukatorplus.model.AuthRequest;
import com.edukatorplus.model.AuthResponse;
import com.edukatorplus.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin // makni ako imaš globalni CORS
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** Health-check za frontend */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    /** Login: prima email+password; vraća token + rolu */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse resp = authService.login(request);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login error");
        }
    }

    /**
     * Registracija: rola dolazi iz query parametra (?role=ADMIN|USER); default USER.
     * Body je samo email+password (AuthRequest).
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam(value = "role", required = false, defaultValue = "USER") String role,
            @RequestBody AuthRequest request) {
        try {
            authService.register(request, role.toUpperCase());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration error");
        }
    }

    /** Brza ruta za izradu ADMIN-a (zaključaj u SecurityConfigu za prod) */
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        try {
            authService.register(request, "ADMIN");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration error");
        }
    }
}
