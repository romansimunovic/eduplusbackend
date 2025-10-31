package com.edukatorplus.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/radionice")
public class RadionicaController {

    @GetMapping
    public List<Map<String, Object>> getRadionice() {
        return List.of(
            Map.of("id", 1, "naziv", "Digitalni marketing", "datum", "2025-11-05"),
            Map.of("id", 2, "naziv", "Osnove programiranja", "datum", "2025-11-12"),
            Map.of("id", 3, "naziv", "Uvod u projektni menadžment", "datum", "2025-11-20")
        );
    }
}
