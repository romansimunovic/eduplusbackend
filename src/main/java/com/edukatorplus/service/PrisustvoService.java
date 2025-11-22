package com.edukatorplus.service;

import com.edukatorplus.model.Prisustvo;
import com.edukatorplus.model.StatusPrisustva;
import com.edukatorplus.repository.PrisustvoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class PrisustvoService {
    @Autowired
    private PrisustvoRepository repository;

    public List<Prisustvo> getAll() { return repository.findAll(); }

    public Prisustvo getById(Long id) { return repository.findById(id).orElse(null); }

    public List<Prisustvo> getByRadionica(Long radionicaId) { return repository.findByRadionicaId(radionicaId); }

    public List<Prisustvo> getByPolaznik(Long polaznikId) { return repository.findByPolaznikId(polaznikId); }

    public List<Prisustvo> getByStatus(StatusPrisustva status) { return repository.findByStatus(status); }

    public Prisustvo saveOrUpdate(Prisustvo p) { return repository.save(p); }

    public void delete(Long id) { repository.deleteById(id); }

    // Statistika primjer
    public Map<String, Object> statistika() {
        List<Prisustvo> all = repository.findAll();
        long prisutni = all.stream().filter(p -> p.getStatus() == StatusPrisustva.PRISUTAN).count();
        long izostali = all.stream().filter(p -> p.getStatus() == StatusPrisustva.IZOSTAO).count();
        long odustali = all.stream().filter(p -> p.getStatus() == StatusPrisustva.ODUSTAO).count();
        Map<String, Object> stats = new HashMap<>();
        stats.put("prisutni", prisutni);
        stats.put("izostali", izostali);
        stats.put("odustali", odustali);
        return stats;
    }
}
