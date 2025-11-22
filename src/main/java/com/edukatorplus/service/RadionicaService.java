package com.edukatorplus.service;

import com.edukatorplus.model.Radionica;
import com.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RadionicaService {
    @Autowired
    private RadionicaRepository repository;

    public List<Radionica> getAll() { return repository.findAll(); }
    public Radionica getById(Long id) { return repository.findById(id).orElse(null); }
    public Radionica saveOrUpdate(Radionica r) { return repository.save(r); }
    public void delete(Long id) { repository.deleteById(id); }
    public List<Radionica> searchByNaziv(String naziv) { return repository.findByNazivContainingIgnoreCase(naziv); }
    public List<Radionica> getAktivne() { return repository.findByAktivnaTrue(); }
}
