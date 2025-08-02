package com.edukatorplus.controller;

import com.edukatorplus.seeder.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dev")
public class DevController {

    @Autowired
    private DataSeeder dataSeeder;

    @PostMapping("/seed")
    public ResponseEntity<String> regenerateData() {
        dataSeeder.generateNewData();
        return ResponseEntity.ok("Podaci su ponovno generirani.");
    }
}
