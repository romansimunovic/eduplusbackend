package com.edukatorplus.controller;

import com.edukatorplus.config.DevDataSeeder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin", produces = "application/json")
public class AdminToolsController {

    private final DataSeeder seeder;

    public AdminToolsController(DataSeeder seeder) {
        this.seeder = seeder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/seed")
    public ResponseEntity<Map<String,String>> seed() {
        try {
            seeder.reseedAllDestructive();
            return ResponseEntity.ok(Map.of("status","success","message","Podaci regenerirani."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("status","error","message", e.getMessage()));
        }
    }
}
