package com.edukatorplus.repository;

import com.edukatorplus.model.Prisustvo;
import com.edukatorplus.model.StatusPrisustva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PrisustvoRepository extends JpaRepository<Prisustvo, Long> {

    // Dohvat prema ID-u polaznika
    List<Prisustvo> findByPolaznikId(Long polaznikId);

    // Dohvat prema ID-u radionice
    List<Prisustvo> findByRadionicaId(Long radionicaId);

    // Dohvat prema statusu kao string
    List<Prisustvo> findByStatus(String status);

    // Dohvat prema statusu kao enum
    List<Prisustvo> findByStatus(StatusPrisustva status);

    // Brisanje svih prisustava povezanih s određenom radionicom
    @Modifying
    @Transactional
    @Query("DELETE FROM Prisustvo p WHERE p.radionica.id = :id")
    void deleteByRadionica_Id(Long id);

    // Brisanje svih prisustava povezanih s određenim polaznikom
    @Modifying
    @Transactional
    @Query("DELETE FROM Prisustvo p WHERE p.polaznik.id = :id")
    void deleteByPolaznik_Id(Long id);
}
