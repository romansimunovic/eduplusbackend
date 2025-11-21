package com.edukatorplus.service;

import com.edukatorplus.model.Polaznik;
import com.edukatorplus.repository.PolaznikRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolaznikService {
    @Autowired
    private PolaznikRepository repo;

    public List<Polaznik> getAll() { return repo.findAll(); }
    public Polaznik getById(Long id) { return repo.findById(id).orElse(null); }
    public Polaznik saveOrUpdate(Polaznik polaznik) { return repo.save(polaznik); }
    public void delete(Long id) { repo.deleteById(id); }
}
