package com.edukatorplus.repository;

import com.edukatorplus.model.Radionica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadionicaRepository extends JpaRepository<Radionica, Long> {
    // Možemo dodati metodu za pretragu prema nazivu, ako je potrebno
    // Na primjer, metoda za pretragu radionica prema nazivu
    List<Radionica> findByNazivContainingIgnoreCase(String naziv);
}
