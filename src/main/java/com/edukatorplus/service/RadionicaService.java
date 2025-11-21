package com.edukatorplus.service;

import com.edukatorplus.model.Radionica;
import com.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadionicaService {
    @Autowired
    private RadionicaRepository repo;

    public List<Radionica> getAll() { return repo.findAll(); }
    public Radionica getById(Long id) { return repo.findById(id).orElse(null); }
    public Radionica saveOrUpdate(Radionica r) { return repo.save(r); }
    public void delete(Long id) { repo.deleteById(id); }
}
