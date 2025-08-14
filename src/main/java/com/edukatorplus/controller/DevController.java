package com.edukatorplus.controller;

import com.edukatorplus.config.DevDataSeeder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("dev") // radi samo u dev profilu
@RestController
@RequestMapping("/api/dev")
public class DevController {

    private final DevDataSeeder seeder;

    public DevController(DevDataSeeder seeder) {
        this.seeder = seeder;
    }

    @PostMapping("/seed")
    public ResponseEntity<Map<String, String>> regenerateData() {
        try {
            // CommandLineRunner metoda; poziv bez argumenata je ok (varargs)
            seeder.run();
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Podaci su ponovno generirani."
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Greška pri regeneriranju podataka: " + e.getMessage()
            ));
        }
    }
}
