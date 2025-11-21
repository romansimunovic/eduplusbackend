package com.edukatorplus.service;

import com.edukatorplus.model.User;
import com.edukatorplus.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean checkPassword(String raw, String enc) {
        return passwordEncoder.matches(raw, enc);
    }
    public void seedAdmin(String email, String pass, String ime, String prezime) {
        if(!existsByEmail(email)) {
            User admin = new User();
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(pass));
            admin.setIme(ime);
            admin.setPrezime(prezime);
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
    }
}
