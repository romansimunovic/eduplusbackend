package com.edukatorplus.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/polaznici")
public class PolaznikController {

    @GetMapping
    public List<Map<String, Object>> getPolaznici() {
        return List.of(
            Map.of("id", 1, "ime", "Ana", "prezime", "Marić", "email", "ana.maric@gmail.com", "spol", "Ž", "grad", "Osijek", "status", "student", "telefon", "091234567", "godinaRodenja", 2001),
            Map.of("id", 2, "ime", "Marko", "prezime", "Ivić", "email", "marko.ivic@gmail.com", "spol", "M", "grad", "Zagreb", "status", "zaposlen", "telefon", "092345678", "godinaRodenja", 1998)
        );
    }
}
