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

@Service
public class PrisustvoService {

    @Autowired
    private PrisustvoRepository prisustvoRepository;

    @Autowired
    private PolaznikRepository polaznikRepository;

    @Autowired
    private RadionicaRepository radionicaRepository;

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

        return new PrisustvoDTO(prisustvo.getId(), polaznik.getId(), radionica.getId(), prisustvo.getStatus());
    }

    public List<PrisustvoDTO> getAllPrisustva() {
        return prisustvoRepository.findAll().stream()
                .map(p -> new PrisustvoDTO(
                        p.getId(),
                        p.getPolaznik().getId(),
                        p.getRadionica().getId(),
                        p.getStatus()))
                .toList();
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
        return new PrisustvoDTO(prisustvo.getId(), polaznik.getId(), radionica.getId(), prisustvo.getStatus());
    }

    public PrisustvoDTO getPrisustvo(Long id) {
        Prisustvo prisustvo = prisustvoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prisustvo nije pronađeno"));
        return new PrisustvoDTO(
                prisustvo.getId(),
                prisustvo.getPolaznik().getId(),
                prisustvo.getRadionica().getId(),
                prisustvo.getStatus()
        );
    }

    public void deletePrisustvo(Long id) {
        prisustvoRepository.deleteById(id);
    }

    // ✅ Metoda za rodno osjetljiv tekst statusa
    public static String getRodnoOsjetljivStatus(StatusPrisustva status, String spol) {
        if (spol == null) return "nepoznato";

        switch (status) {
            case PRISUTAN:
                return spol.equalsIgnoreCase("ž") ? "prisutna" : "prisutan";
            case IZOSTAO:
                return spol.equalsIgnoreCase("ž") ? "izostala" : "izostao";
            case ODUSTAO:
                return spol.equalsIgnoreCase("ž") ? "odustala" : "odustao";
            case NEPOZNATO:
            default:
                return "nepoznato";
        }
    }

    // ✅ Prikaz za frontend s rodom (spol)
    public List<PrisustvoViewDTO> getAllForDisplay() {
        return prisustvoRepository.findAll().stream()
                .map(p -> new PrisustvoViewDTO(
                        p.getId(),
                        p.getPolaznik().getIme() + " " + p.getPolaznik().getPrezime(),
                        p.getRadionica().getNaziv(),
                        getRodnoOsjetljivStatus(p.getStatus(), p.getPolaznik().getSpol()),
                        p.getPolaznik().getSpol()
                ))
                .toList();
    }
}
