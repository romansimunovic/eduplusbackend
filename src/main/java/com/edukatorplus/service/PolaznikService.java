package com.edukatorplus.service;

import com.edukatorplus.dto.PolaznikDTO;
import com.edukatorplus.model.Polaznik;
import com.edukatorplus.repository.PolaznikRepository;
import com.edukatorplus.repository.PrisustvoRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PolaznikService {

    @Autowired
    private PolaznikRepository polaznikRepository;

    @Autowired
    private PrisustvoRepository prisustvoRepository;

    private PolaznikDTO toDTO(Polaznik p) {
        return new PolaznikDTO(
                p.getId(),
                p.getIme(),
                p.getPrezime(),
                p.getEmail(),
                p.getGodinaRodenja()
        );
    }

    public List<PolaznikDTO> getAll() {
        return polaznikRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PolaznikDTO getById(Long id) {
        return polaznikRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Polaznik s ID-om " + id + " nije pronađen"));
    }

    public PolaznikDTO create(PolaznikDTO dto) {
        Polaznik p = new Polaznik(dto.ime(), dto.prezime(), dto.email(), dto.godinaRodenja());
        return toDTO(polaznikRepository.save(p));
    }

    public PolaznikDTO update(Long id, PolaznikDTO dto) {
        Polaznik p = polaznikRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Polaznik s ID-om " + id + " nije pronađen"));

        p.setIme(dto.ime());
        p.setPrezime(dto.prezime());
        p.setEmail(dto.email());
        p.setGodinaRodenja(dto.godinaRodenja());

        return toDTO(polaznikRepository.save(p));
    }

    public void delete(Long id) {
        Polaznik p = polaznikRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Polaznik s ID-om " + id + " nije pronađen"));

        // 1. Obriši prisustva povezana s polaznikom
        prisustvoRepository.deleteByPolaznik_Id(id);

        // 2. Obriši polaznika
        polaznikRepository.delete(p);
    }
}
