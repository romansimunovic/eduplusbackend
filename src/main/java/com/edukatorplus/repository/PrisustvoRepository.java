package com.edukatorplus.repository;

import com.edukatorplus.model.Prisustvo;
import com.edukatorplus.model.StatusPrisustva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrisustvoRepository extends JpaRepository<Prisustvo, Long> {
    List<Prisustvo> findByRadionicaId(Long radionicaId);
    List<Prisustvo> findByPolaznikId(Long polaznikId);
    List<Prisustvo> findByStatus(StatusPrisustva status);
}
