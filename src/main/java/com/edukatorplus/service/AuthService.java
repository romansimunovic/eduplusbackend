package com.edukatorplus.service;

import com.edukatorplus.model.AppUser;
import com.edukatorplus.model.AuthRequest;
import com.edukatorplus.model.AuthResponse;
import com.edukatorplus.repository.AppUserRepository;
import com.edukatorplus.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        AppUser user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Pogrešna lozinka");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token);
    }

    public void register(AuthRequest request, String role) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email već postoji");
        }

        AppUser newUser = new AppUser();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(role);

        userRepo.save(newUser);
    }
}
