package com.edukatorplus.controller;

import com.edukatorplus.model.User;
import com.edukatorplus.service.UserService;
import com.edukatorplus.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "https://eduplusfrontend-8qgi1vkyx-romansimunovics-projects.vercel.app")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserService service;
    @Autowired private JWTUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if(service.existsByEmail(user.getEmail())) return "Email već postoji!";
        service.register(user);
        return "Registracija uspješna";
    }

    @PostMapping("/login")
    public String login(@RequestBody User payload) {
        User user = service.findByEmail(payload.getEmail());
        if(user!=null && service.checkPassword(payload.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user);
            return token;
        }
        return "Neispravni podaci!";
    }
}
