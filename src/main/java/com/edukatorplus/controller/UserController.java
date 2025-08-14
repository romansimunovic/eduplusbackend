package com.edukatorplus.controller;

import com.edukatorplus.repository.AppUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AppUserRepository userRepo;

    public UserController(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<UserSummary> getAllUsers() {
        return userRepo.findAll().stream()
                .map(u -> new UserSummary(u.getId(), u.getEmail(), u.getRole()))
                .toList();
    }

    // mali DTO da ne vraćamo password
    public record UserSummary(Long id, String email, String role) {}
}
