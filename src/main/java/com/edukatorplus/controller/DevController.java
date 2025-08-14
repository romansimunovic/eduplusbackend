package com.edukatorplus.controller;

import com.edukatorplus.config.DevDataSeeder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("dev") // endpoint dostupan samo u dev okruženju
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
            // pokreće generiranje novih podataka
            seeder.run();
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Podaci su uspješno regenerirani."
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Greška: " + e.getMessage()
            ));
        }
    }
}
