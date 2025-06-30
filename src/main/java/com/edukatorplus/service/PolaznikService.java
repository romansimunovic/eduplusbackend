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

    // Pretvaranje Polaznik entiteta u PolaznikDTO
    private PolaznikDTO toDTO(Polaznik p) {
        return new PolaznikDTO(
                p.getId(),
                p.getIme(),
                p.getPrezime(),
                p.getEmail(),
                p.getGodinaRođenja()
        );
    }

    // Dohvaća sve polaznike
    @ApiResponse(responseCode = "200", description = "Polaznici pronađeni")
    public List<PolaznikDTO> getAll() {
        return polaznikRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Dohvaća polaznika prema ID-u
    @ApiResponse(responseCode = "200", description = "Polaznik pronađen")
    @ApiResponse(responseCode = "404", description = "Polaznik nije pronađen")
    public PolaznikDTO getById(Long id) {
        return polaznikRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Polaznik s ID-om " + id + " nije pronađen"));
    }

    // Kreira novog polaznika
    @ApiResponse(responseCode = "201", description = "Polaznik uspješno kreiran")
    public PolaznikDTO create(PolaznikDTO dto) {
        Polaznik p = new Polaznik(dto.ime(), dto.prezime(), dto.email(), dto.godinaRođenja());
        return toDTO(polaznikRepository.save(p));
    }

    // Ažurira polaznika prema ID-u
    @ApiResponse(responseCode = "200", description = "Polaznik uspješno ažuriran")
    @ApiResponse(responseCode = "404", description = "Polaznik nije pronađen")
    public PolaznikDTO update(Long id, PolaznikDTO dto) {
        Optional<Polaznik> opt = polaznikRepository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Polaznik s ID-om " + id + " nije pronađen");
        }
        Polaznik p = opt.get();
        p.setIme(dto.ime());
        p.setPrezime(dto.prezime());
        p.setEmail(dto.email());
        p.setGodinaRođenja(dto.godinaRođenja());
        return toDTO(polaznikRepository.save(p));
    }

    // Briše polaznika prema ID-u
    @ApiResponse(responseCode = "200", description = "Polaznik uspješno obrisan")
    @ApiResponse(responseCode = "404", description = "Polaznik nije pronađen")
    public void delete(Long id) {
        // Provjera da li polaznik postoji
        Optional<Polaznik> opt = polaznikRepository.findById(id);
        if (opt.isPresent()) {
            Polaznik p = opt.get();

            // Brišemo prvo sva prisustva vezana uz polaznika
            prisustvoRepository.deleteByPolaznikId(id); // Brišemo prisustva po ID-u polaznika

            // Brišemo polaznika
            polaznikRepository.delete(p);
        } else {
            throw new RuntimeException("Polaznik s ID-om " + id + " nije pronađen");
        }
    }
}
