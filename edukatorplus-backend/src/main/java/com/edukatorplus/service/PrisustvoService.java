package com.edukatorplus.service;

import com.edukatorplus.dto.PrisustvoDTO;
import com.edukatorplus.model.Polaznik;
import com.edukatorplus.model.Prisustvo;
import com.edukatorplus.model.Radionica;
import com.edukatorplus.repository.PrisustvoRepository;
import com.edukatorplus.repository.PolaznikRepository;
import com.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrisustvoService {

    @Autowired
    private PrisustvoRepository prisustvoRepository;

    @Autowired
    private PolaznikRepository polaznikRepository;

    @Autowired
    private RadionicaRepository radionicaRepository;

    // Spremanje novog prisustva
    public PrisustvoDTO savePrisustvo(PrisustvoDTO prisustvoDTO) {
        // Dohvaćanje Polaznika i Radionice prema ID-ovima
        Polaznik polaznik = polaznikRepository.findById(prisustvoDTO.polaznikId()).orElseThrow(() -> new RuntimeException("Polaznik nije pronađen"));
        Radionica radionica = radionicaRepository.findById(prisustvoDTO.radionicaId()).orElseThrow(() -> new RuntimeException("Radionica nije pronađena"));

        // Kreiranje novog Prisustva
        Prisustvo prisustvo = new Prisustvo();
        prisustvo.setPolaznikId(polaznik.getId());  // Postavljanje ID-a polaznika
        prisustvo.setRadionicaId(radionica.getId());  // Postavljanje ID-a radionice
        prisustvo.setStatus(prisustvoDTO.status());  // Postavljanje statusa

        prisustvo = prisustvoRepository.save(prisustvo);  // Spremanje u bazu
        return new PrisustvoDTO(prisustvo.getId(), prisustvo.getPolaznikId(), prisustvo.getRadionicaId(), prisustvo.getStatus());
    }

    // Ažurira prisustvo prema ID-u
    public PrisustvoDTO updatePrisustvo(Long id, PrisustvoDTO prisustvoDTO) {
        Prisustvo prisustvo = prisustvoRepository.findById(id).orElseThrow(() -> new RuntimeException("Prisustvo nije pronađeno"));

        // Dohvaćanje Polaznika i Radionice prema ID-ovima
        Polaznik polaznik = polaznikRepository.findById(prisustvoDTO.polaznikId()).orElseThrow(() -> new RuntimeException("Polaznik nije pronađen"));
        Radionica radionica = radionicaRepository.findById(prisustvoDTO.radionicaId()).orElseThrow(() -> new RuntimeException("Radionica nije pronađena"));

        // Ažuriranje prisustva
        prisustvo.setPolaznikId(polaznik.getId());
        prisustvo.setRadionicaId(radionica.getId());
        prisustvo.setStatus(prisustvoDTO.status());

        prisustvo = prisustvoRepository.save(prisustvo);  // Spremanje u bazu
        return new PrisustvoDTO(prisustvo.getId(), prisustvo.getPolaznikId(), prisustvo.getRadionicaId(), prisustvo.getStatus());
    }

    // Dohvaća sva prisustva
    public List<PrisustvoDTO> getAllPrisustva() {
        List<Prisustvo> prisustva = prisustvoRepository.findAll();
        return prisustva.stream()
                .map(p -> new PrisustvoDTO(p.getId(), p.getPolaznikId(), p.getRadionicaId(), p.getStatus()))
                .collect(Collectors.toList());
    }

    // Dohvaća jedno prisustvo prema ID-u
    public PrisustvoDTO getPrisustvo(Long id) {
        Optional<Prisustvo> prisustvo = prisustvoRepository.findById(id);
        return prisustvo.map(p -> new PrisustvoDTO(p.getId(), p.getPolaznikId(), p.getRadionicaId(), p.getStatus())).orElse(null);
    }

    // Briše prisustvo prema ID-u
    public void deletePrisustvo(Long id) {
        prisustvoRepository.deleteById(id); // Brišemo samo prisustvo, ne polaznike ili radionice
    }
}
