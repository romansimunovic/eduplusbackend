package com.edukatorplus.service;

import com.edukatorplus.model.Prisustvo;
import com.edukatorplus.model.StatusPrisustva;
import com.edukatorplus.repository.PrisustvoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrisustvoService {
    @Autowired
    private PrisustvoRepository repo;

    public List<Prisustvo> getAll() { return repo.findAll(); }
    public Prisustvo getById(Long id) { return repo.findById(id).orElse(null); }
    public List<Prisustvo> getByRadionica(Long radionicaId) { return repo.findByRadionicaId(radionicaId); }
    public List<Prisustvo> getByPolaznik(Long polaznikId) { return repo.findByPolaznikId(polaznikId); }
    public List<Prisustvo> getByStatus(StatusPrisustva status) { return repo.findByStatus(status); }
    public Prisustvo saveOrUpdate(Prisustvo p) { return repo.save(p); }
    public void delete(Long id) { repo.deleteById(id); }
}
