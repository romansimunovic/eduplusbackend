package com.edukatorplus.repository;
import com.edukatorplus.model.StatusPrisustva;
import com.edukatorplus.model.Prisustvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrisustvoRepository extends JpaRepository<Prisustvo, Long> {

    List<Prisustvo> findByPolaznikId(Long polaznikId);

    List<Prisustvo> findByRadionicaId(Long radionicaId);

    List<Prisustvo> findByStatus(String status);

    List<Prisustvo> findByStatus(StatusPrisustva status);

    void deleteByRadionica_Id(Long id);

    void deleteByPolaznik_Id(Long id); 
}
