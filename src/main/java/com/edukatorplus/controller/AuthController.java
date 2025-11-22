package com.edukatorplus.controller;

import com.edukatorplus.model.User;
import com.edukatorplus.service.UserService;
import com.edukatorplus.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"https://eduplusfrontend.vercel.app", "http://localhost:3000"})
public class AuthController {
    @Autowired private UserService service;
    @Autowired private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if(service.existsByEmail(user.getEmail())) return ResponseEntity.badRequest().body("Email već postoji!");
        service.register(user);
        return ResponseEntity.ok("Registracija uspješna");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User payload) {
        try {
            User user = service.findByEmail(payload.getEmail());
            if(user!=null && service.checkPassword(payload.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user);
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.status(401).body("Neispravni podaci!");
        } catch (Exception ex) {
            ex.printStackTrace(); // ide direktno u Render logs
            return ResponseEntity.status(500).body("Server error: " + ex.getMessage());
        }
    }
}
