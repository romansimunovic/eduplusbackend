package com.edukatorplus.controller;

import com.edukatorplus.config.DevDataSeeder; // ili DataSeeder, ovisno kako ti se zove klasa
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin", produces = "application/json")
public class AdminToolsController {

    private final DevDataSeeder seeder;

    public AdminToolsController(DevDataSeeder seeder) {
        this.seeder = seeder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/seed")
    public ResponseEntity<Map<String,String>> seed() {
        try {
            seeder.run(); // generira nove podatke
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Podaci regenerirani."
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "status", "error",
                            "message", e.getMessage()
                    ));
        }
    }
}
