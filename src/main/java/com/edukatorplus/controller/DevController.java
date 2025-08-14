package com.edukatorplus.controller;

import com.edukatorplus.config.DevDataSeeder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("dev") // endpoint postoji samo u 'dev' profilu
@RestController
@RequestMapping(value = "/api/dev", produces = "application/json")
public class DevController {

    private final DevDataSeeder seeder;

    public DevController(DevDataSeeder seeder) {
        this.seeder = seeder;
    }

    @PostMapping("/seed")
    public ResponseEntity<Map<String, String>> regenerateData() {
        try {
            // generira nove random podatke i sprema u bazu (stare briše po tvojoj logici)
            seeder.run(); // varargs metoda; poziv bez argumenata je OK
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Podaci su uspješno regenerirani."
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Greška pri regeneriranju podataka: " + e.getMessage()
            ));
        }
    }
}
