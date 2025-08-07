package com.edukatorplus.service;

import com.edukatorplus.dto.PrisustvoDTO;
import com.edukatorplus.dto.PrisustvoViewDTO;
import com.edukatorplus.model.Polaznik;
import com.edukatorplus.model.Prisustvo;
import com.edukatorplus.model.Radionica;
import com.edukatorplus.model.StatusPrisustva;
import com.edukatorplus.repository.PolaznikRepository;
import com.edukatorplus.repository.PrisustvoRepository;
import com.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrisustvoService {

    @Autowired
    private PrisustvoRepository prisustvoRepository;

    @Autowired
    private PolaznikRepository polaznikRepository;

    @Autowired
    private RadionicaRepository radionicaRepository;

    // Dodavanje i ažuriranje
    public PrisustvoDTO savePrisustvo(PrisustvoDTO dto) {
        Polaznik polaznik = polaznikRepository.findById(dto.polaznikId())
                .orElseThrow(() -> new RuntimeException("Polaznik nije pronađen"));
        Radionica radionica = radionicaRepository.findById(dto.radionicaId())
                .orElseThrow(() -> new RuntimeException("Radionica nije pronađena"));

        Prisustvo prisustvo = new Prisustvo();
        prisustvo.setPolaznik(polaznik);
        prisustvo.setRadionica(radionica);
        prisustvo.setStatus(dto.status());

        prisustvo = prisustvoRepository.save(prisustvo);
        return toDto(prisustvo);
    }

    public PrisustvoDTO updatePrisustvo(Long id, PrisustvoDTO dto) {
        Prisustvo prisustvo = prisustvoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prisustvo nije pronađeno"));

        Polaznik polaznik = polaznikRepository.findById(dto.polaznikId())
                .orElseThrow(() -> new RuntimeException("Polaznik nije pronađen"));
        Radionica radionica = radionicaRepository.findById(dto.radionicaId())
                .orElseThrow(() -> new RuntimeException("Radionica nije pronađena"));

        prisustvo.setPolaznik(polaznik);
        prisustvo.setRadionica(radionica);
        prisustvo.setStatus(dto.status());

        prisustvo = prisustvoRepository.save(prisustvo);
        return toDto(prisustvo);
    }

    // Dohvaćanje svih prisustava (u DTO formatu)
    public List<PrisustvoDTO> getAllPrisustva() {
        return prisustvoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PrisustvoDTO getPrisustvo(Long id) {
        return prisustvoRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Prisustvo nije pronađeno"));
    }

    public void deletePrisustvo(Long id) {
        prisustvoRepository.deleteById(id);
    }

    // Za prikaz (ViewDTO)
    public List<PrisustvoViewDTO> getAllForDisplay() {
        return prisustvoRepository.findAll().stream()
                .map(this::toViewDto)
                .collect(Collectors.toList());
    }

    public List<PrisustvoViewDTO> getAllForRadionica(Long radionicaId) {
        return prisustvoRepository.findAll().stream()
                .filter(p -> p.getRadionica().getId().equals(radionicaId))
                .map(this::toViewDto)
                .collect(Collectors.toList());
    }

    public List<PrisustvoViewDTO> findByPolaznikId(Long id) {
        return prisustvoRepository.findByPolaznikId(id).stream()
                .map(this::toViewDto)
                .collect(Collectors.toList());
    }

    // Helper metode
    private PrisustvoDTO toDto(Prisustvo p) {
        return new PrisustvoDTO(
                p.getId(),
                p.getPolaznik().getId(),
                p.getRadionica().getId(),
                p.getStatus()
        );
    }

    private PrisustvoViewDTO toViewDto(Prisustvo p) {
        return new PrisustvoViewDTO(
                p.getId(),
                p.getPolaznik().getId(),
                p.getRadionica().getId(),
                p.getPolaznik().getIme() + " " + p.getPolaznik().getPrezime(),
                p.getRadionica().getNaziv(),
                p.getStatus(),
                getRodnoOsjetljivStatus(p.getStatus(), p.getPolaznik().getSpol())
        );
    }

    public static String getRodnoOsjetljivStatus(StatusPrisustva status, String spol) {
        if (spol == null) return "nepoznato";

        return switch (status) {
            case PRISUTAN -> spol.equalsIgnoreCase("ž") ? "prisutna" : "prisutan";
            case IZOSTAO -> spol.equalsIgnoreCase("ž") ? "izostala" : "izostao";
            case ODUSTAO -> spol.equalsIgnoreCase("ž") ? "odustala" : "odustao";
            case NEPOZNATO -> "nepoznato";
        };
    }
}
