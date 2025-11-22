package com.edukatorplus.controller;

import com.edukatorplus.model.Polaznik;
import com.edukatorplus.service.PolaznikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/polaznici")
@CrossOrigin(origins = {"https://eduplusfrontend.vercel.app", "http://localhost:3000"})
public class PolaznikController {
    @Autowired
    private PolaznikService service;

    @GetMapping
    public List<Polaznik> getPolaznici() { return service.getAll(); }

    @GetMapping("/search")
    public List<Polaznik> search(@RequestParam String q) { return service.search(q); }

    @GetMapping("/{id}")
    public Polaznik getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public Polaznik create(@RequestBody Polaznik p) { return service.saveOrUpdate(p); }

    @PutMapping("/{id}")
    public Polaznik update(@PathVariable Long id, @RequestBody Polaznik p) {
        p.setId(id);
        return service.saveOrUpdate(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
