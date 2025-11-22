package com.edukatorplus.repository;

import com.edukatorplus.model.Radionica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RadionicaRepository extends JpaRepository<Radionica, Long> {
    List<Radionica> findByNazivContainingIgnoreCase(String naziv);
    List<Radionica> findByAktivnaTrue();
}
