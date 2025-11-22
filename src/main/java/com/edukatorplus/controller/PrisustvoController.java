package com.edukatorplus.controller;

import com.edukatorplus.model.Prisustvo;
import com.edukatorplus.model.StatusPrisustva;
import com.edukatorplus.service.PrisustvoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;

@RestController
@RequestMapping("/api/prisustva")
@CrossOrigin(origins = {"https://tvoj-frontend.vercel.app", "http://localhost:3000"})
public class PrisustvoController {
    @Autowired private PrisustvoService service;

    @GetMapping
    public List<Prisustvo> getAll() { return service.getAll(); }
    @GetMapping("/{id}")
    public Prisustvo getById(@PathVariable Long id) { return service.getById(id); }
    @GetMapping("/radionica/{radionicaId}")
    public List<Prisustvo> getByRadionica(@PathVariable Long radionicaId) { return service.getByRadionica(radionicaId); }
    @GetMapping("/polaznik/{polaznikId}")
    public List<Prisustvo> getByPolaznik(@PathVariable Long polaznikId) { return service.getByPolaznik(polaznikId); }
    @GetMapping("/status/{status}")
    public List<Prisustvo> getByStatus(@PathVariable StatusPrisustva status) { return service.getByStatus(status); }
    @PostMapping
    public Prisustvo create(@RequestBody Prisustvo p) { return service.saveOrUpdate(p); }
    @PutMapping("/{id}")
    public Prisustvo update(@PathVariable Long id, @RequestBody Prisustvo p) { p.setId(id); return service.saveOrUpdate(p);}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/statistika")
    public Map<String, Object> statistika() { return service.statistika(); }

    @GetMapping("/export/csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=prisustva.csv");
        List<Prisustvo> list = service.getAll();
        PrintWriter writer = response.getWriter();
        writer.println("ID,PolaznikId,RadionicaId,Status");
        for (Prisustvo p : list) {
            writer.println(String.join(",", 
                p.getId().toString(), 
                p.getPolaznikId().toString(),
                p.getRadionicaId().toString(),
                p.getStatus().name()));
        }
        writer.flush();
    }
}
