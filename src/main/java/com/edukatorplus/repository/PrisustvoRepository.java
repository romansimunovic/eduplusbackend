package com.edukatorplus.repository;
import com.edukatorplus.model.StatusPrisustva;
import com.edukatorplus.model.Prisustvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrisustvoRepository extends JpaRepository<Prisustvo, Long> {

    // Pretraga prisustva prema polazniku
    List<Prisustvo> findByPolaznikId(Long polaznikId);

    // Pretraga prisustva prema radionici
    List<Prisustvo> findByRadionicaId(Long radionicaId);

    // Pretraga prisustva prema statusu
    List<Prisustvo> findByStatus(String status);

    // Pretraga prisustva prema polazniku i radionici
    List<Prisustvo> findByStatus(StatusPrisustva status);

    void deleteByRadionica_Id(Long id);
}
