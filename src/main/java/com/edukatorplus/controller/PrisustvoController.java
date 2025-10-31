package com.edukatorplus.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/prisustva")
public class PrisustvoController {

    @GetMapping
    public List<Map<String, Object>> getPrisustva() {
        return List.of(
            Map.of("polaznikImePrezime", "Ana Marić", "radionicaNaziv", "Digitalni marketing", "status", "PRISUTAN"),
            Map.of("polaznikImePrezime", "Marko Ivić", "radionicaNaziv", "Digitalni marketing", "status", "NEPOZNATO"),
            Map.of("polaznikImePrezime", "Ana Marić", "radionicaNaziv", "Osnove programiranja", "status", "IZOSTAO"),
            Map.of("polaznikImePrezime", "Marko Ivić", "radionicaNaziv", "Uvod u projektni menadžment", "status", "ODUSTAO")
        );
    }
}
