package com.edukatorplus.controller;

import com.edukatorplus.model.Radionica;
import com.edukatorplus.service.RadionicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radionice")
public class RadionicaController {
    @Autowired private RadionicaService service;

    @GetMapping public List<Radionica> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Radionica getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Radionica create(@RequestBody Radionica r) { return service.saveOrUpdate(r); }
    @PutMapping("/{id}") public Radionica update(@PathVariable Long id, @RequestBody Radionica r) {
        r.setId(id); return service.saveOrUpdate(r);
    }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
