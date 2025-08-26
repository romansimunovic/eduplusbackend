package com.edukatorplus.service;

import com.edukatorplus.model.AppUser;
import com.edukatorplus.model.AuthRequest;
import com.edukatorplus.model.AuthResponse;
import com.edukatorplus.repository.AppUserRepository;
import com.edukatorplus.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AppUserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest request) {
        if (request == null || isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Email i lozinka su obavezni.");
        }

        final String email = request.getEmail().trim().toLowerCase();
        AppUser user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronađen."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Pogrešna lozinka.");
        }

        // Role iz baze -> UPPERCASE (ADMIN/USER)
        final String role = normalizeRole(user.getRole());
        final String token = jwtUtil.generateToken(user.getEmail(), role);

        // vrati i token i role (frontend očekuje)
        return new AuthResponse(token, role);
    }

    public void register(AuthRequest request, String roleFromController) {
        if (request == null || isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Email i lozinka su obavezni.");
        }

        final String email = request.getEmail().trim().toLowerCase();
        if (userRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email već postoji.");
        }

        final String role = normalizeRole(roleFromController);

        AppUser newUser = new AppUser();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(role); // ako ti je u entitetu enum, mapiraj na enum vrijednost

        userRepo.save(newUser);
    }

    // ——— helpers ———
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String normalizeRole(String role) {
        return (role == null || role.isBlank()) ? "USER" : role.trim().toUpperCase();
    }
}
