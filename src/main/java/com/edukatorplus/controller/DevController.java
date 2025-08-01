package com.edukatorplus.controller;

import com.edukatorplus.seeder.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dev")
public class DevController {

    @Autowired
    private DataSeeder seeder;

    @PostMapping("/seed")
    public ResponseEntity<String> regenerateData() {
        seeder.init();  // Poziva tvoju metodu koja briše sve i sjemeni iznova
        return ResponseEntity.ok("Podaci su ponovno generirani.");
    }
}
