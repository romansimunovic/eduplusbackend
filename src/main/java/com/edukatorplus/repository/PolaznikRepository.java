package com.edukatorplus.repository;

import com.edukatorplus.model.Polaznik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolaznikRepository extends JpaRepository<Polaznik, Long> {
    // Custom query metode po potrebi
}
