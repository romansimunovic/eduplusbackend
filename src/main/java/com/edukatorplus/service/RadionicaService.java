package com.edukatorplus.service;

import com.edukatorplus.dto.RadionicaDTO;
import com.edukatorplus.model.Radionica;
import com.edukatorplus.repository.RadionicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RadionicaService {

    @Autowired
    private RadionicaRepository radionicaRepository;

    public RadionicaDTO saveRadionica(RadionicaDTO dto) {
        Radionica r = new Radionica(dto.naziv(), dto.datum());
        r = radionicaRepository.save(r);
        return new RadionicaDTO(r.getId(), r.getNaziv(), r.getDatum());
    }

    public RadionicaDTO updateRadionica(Long id, RadionicaDTO dto) {
        Radionica r = radionicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Radionica s ID-om " + id + " nije pronađena"));
        r.setNaziv(dto.naziv());
        r.setDatum(dto.datum());
        r = radionicaRepository.save(r);
        return new RadionicaDTO(r.getId(), r.getNaziv(), r.getDatum());
    }

    public List<RadionicaDTO> getAllRadionice() {
        return radionicaRepository.findAll().stream()
                .map(r -> new RadionicaDTO(r.getId(), r.getNaziv(), r.getDatum()))
                .collect(Collectors.toList());
    }

    public RadionicaDTO getRadionica(Long id) {
        return radionicaRepository.findById(id)
                .map(r -> new RadionicaDTO(r.getId(), r.getNaziv(), r.getDatum()))
                .orElse(null);
    }

    public void deleteRadionica(Long id) {
        radionicaRepository.deleteById(id);
    }


}
