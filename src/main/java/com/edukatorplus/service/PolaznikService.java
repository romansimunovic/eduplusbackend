package com.edukatorplus.service;

import com.edukatorplus.model.Polaznik;
import com.edukatorplus.repository.PolaznikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PolaznikService {
    @Autowired
    private PolaznikRepository repository;

    public List<Polaznik> getAll() { return repository.findAll(); }

    public Polaznik saveOrUpdate(Polaznik p) { return repository.save(p); }

    public List<Polaznik> search(String query) {
        return repository.findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(query, query);
    }

    public Polaznik getById(Long id) { return repository.findById(id).orElse(null); }

    public void delete(Long id) { repository.deleteById(id); }
}
