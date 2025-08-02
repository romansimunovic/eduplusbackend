package com.edukatorplus.repository;

import com.edukatorplus.model.Polaznik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolaznikRepository extends JpaRepository<Polaznik, Long> {

    // Pretraga polaznika prema imenu
    List<Polaznik> findByImeContainingIgnoreCase(String ime);

    // Pretraga polaznika prema prezimenu
    List<Polaznik> findByPrezimeContainingIgnoreCase(String prezime);

    // Pretraga polaznika prema emailu
    List<Polaznik> findByEmailContainingIgnoreCase(String email);

    // Pretraga polaznika prema godini rođenja
    List<Polaznik> findByGodinaRodenja(Integer godinaRodenja);
}
