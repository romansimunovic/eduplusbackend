package com.edukatorplus.service;

import com.edukatorplus.dto.RadionicaDTO;
import com.edukatorplus.model.Radionica;
import com.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RadionicaService {

    @Autowired
    private RadionicaRepository radionicaRepository;

    // Spremanje nove radionice
    public RadionicaDTO saveRadionica(RadionicaDTO radionicaDTO) {
        // Kreiranje novog entiteta Radionica iz DTO
        Radionica radionica = new Radionica(radionicaDTO.naziv());
        
        // Spremanje u bazu podataka
        radionica = radionicaRepository.save(radionica);

        // Vraćanje DTO objekta
        return new RadionicaDTO(radionica.getId(), radionica.getNaziv());
    }

    // Ažurira radionicu prema ID-u
    public RadionicaDTO updateRadionica(Long id, RadionicaDTO radionicaDTO) {
        // Provjera postoji li radionica u bazi
        Radionica radionica = radionicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Radionica s ID-om " + id + " nije pronađena"));

        // Ažuriranje naziva radionice
        radionica.setNaziv(radionicaDTO.naziv());

        // Spremanje ažurirane radionice
        radionica = radionicaRepository.save(radionica);

        // Vraćanje DTO objekta
        return new RadionicaDTO(radionica.getId(), radionica.getNaziv());
    }

    // Dohvaća sve radionice
    public List<RadionicaDTO> getAllRadionice() {
        List<Radionica> radionice = radionicaRepository.findAll();
        return radionice.stream()
                .map(r -> new RadionicaDTO(r.getId(), r.getNaziv()))
                .collect(Collectors.toList());
    }

    // Dohvaća radionicu prema ID-u
    public RadionicaDTO getRadionica(Long id) {
        Optional<Radionica> radionica = radionicaRepository.findById(id);
        return radionica.map(r -> new RadionicaDTO(r.getId(), r.getNaziv())).orElse(null);
    }

    // Briše radionicu prema ID-u
    public void deleteRadionica(Long id) {
        radionicaRepository.deleteById(id); // Brišemo samo radionicu, ne prisustva ili polaznike
    }
}
