package com.edukatorplus.controller;

import com.edukatorplus.seeder.DataSeeder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("dev") // ⬅️ podiže se samo u dev profilu
@RestController
@RequestMapping("/api/dev")
public class DevController {

    private final DataSeeder dataSeeder;

    public DevController(DataSeeder dataSeeder) {
        this.dataSeeder = dataSeeder;
    }

    @PostMapping("/seed")
    public ResponseEntity<String> regenerateData() {
        dataSeeder.generateNewData();
        return ResponseEntity.ok("Podaci su ponovno generirani.");
    }
}
