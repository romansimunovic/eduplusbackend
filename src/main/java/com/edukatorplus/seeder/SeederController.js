package com.edukatorplus.controller;

import com.edukatorplus.seeder.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seed")
public class SeederController {

    @Autowired
    private DataSeeder seeder;

    @PostMapping
    public void regenerate() {
        seeder.generateNewData(); // nova metoda, vidi dolje
    }
}
