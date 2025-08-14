package com.edukatorplus.controller;

import com.edukatorplus.seeder.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("dev")  // ⬅⬅⬅ samo u dev profilu postoji ovaj kontroler
@RestController
@RequestMapping("/api/dev")
public class DevController {

    private final DataSeeder dataSeeder;

    @Autowired
    public DevController(DataSeeder dataSeeder) {
        this.dataSeeder = dataSeeder;
    }

    @PostMapping("/seed")
    public ResponseEntity<String> regenerateData() {
        dataSeeder.generateNewData();
        return ResponseEntity.ok("Podaci su ponovno generirani.");
    }
}
