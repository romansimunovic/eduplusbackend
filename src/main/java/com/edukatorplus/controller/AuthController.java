package com.edukatorplus.controller;

import com.edukatorplus.model.AuthRequest;
import com.edukatorplus.model.AuthResponse;
import com.edukatorplus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin // možeš ukloniti ako imaš globalni CorsFilter
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Jednostavan health-check za frontend (ne vraća nužno JSON).
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    /**
     * Prijava – vraća JWT i rolu u tijelu.
     * 200 OK na uspjeh, 401 na krive kredencijale.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse resp = authService.login(request);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            // npr. “user not found” / “bad credentials”
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login error");
        }
    }

    /**
     * Registracija korisnika.
     * Rola se može zadati kao query param (?role=ADMIN) ili u body-ju (request.role).
     * Ako nije zadano, default je USER.
     * 201 Created na uspjeh.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam(value = "role", required = false) String roleFromQuery,
            @RequestBody AuthRequest request) {

        String role = (roleFromQuery != null && !roleFromQuery.isBlank())
                ? roleFromQuery
                : (request.getRole() != null && !request.getRole().isBlank()
                    ? request.getRole()
                    : "USER");

        try {
            authService.register(request, role.toUpperCase());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            // npr. “email already exists”
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration error");
        }
    }

    /**
     * Eksplicitna ruta za kreiranje ADMIN korisnika (korisno u dev-u).
     * Ako ne želiš je izlagati u produkciji, zabranjuj u SecurityConfigu.
     */
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        try {
            authService.register(request, "ADMIN");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration error");
        }
    }
}