package com.edukatorplus.controller;

import com.edukatorplus.seeder.DataSeeder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("dev") // Dostupno samo kad je aktivan 'dev' profil
@RestController
@RequestMapping("/api/dev")
public class DevController {

    private final DataSeeder dataSeeder;

    public DevController(DataSeeder dataSeeder) {
        this.dataSeeder = dataSeeder;
    }

    @PostMapping("/seed")
    public ResponseEntity<Map<String, String>> regenerateData() {
        dataSeeder.generateNewData();
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Podaci su ponovno generirani."
        ));
    }
}
