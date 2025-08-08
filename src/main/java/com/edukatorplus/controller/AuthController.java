package com.edukatorplus.controller;

import com.edukatorplus.model.AuthRequest;
import com.edukatorplus.model.AuthResponse;
import com.edukatorplus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public void register(@RequestBody AuthRequest request) {
        authService.register(request, "USER");
    }

    @PostMapping("/register-admin")
    public void registerAdmin(@RequestBody AuthRequest request) {
        authService.register(request, "ADMIN");
    }
}
