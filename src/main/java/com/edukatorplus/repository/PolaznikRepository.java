package com.edukatorplus.repository;

import com.edukatorplus.model.Polaznik;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PolaznikRepository extends JpaRepository<Polaznik, Long> {
    List<Polaznik> findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(String ime, String prezime);
}
