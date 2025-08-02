package com.edukatorplus.repository;

import com.edukatorplus.model.Polaznik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolaznikRepository extends JpaRepository<Polaznik, Long> {

    List<Polaznik> findByImeContainingIgnoreCase(String ime);

    List<Polaznik> findByPrezimeContainingIgnoreCase(String prezime);

    List<Polaznik> findByEmailContainingIgnoreCase(String email);

    List<Polaznik> findByGodinaRodenja(Integer godinaRodenja);

    List<Polaznik> findBySpolIgnoreCase(String spol);

    List<Polaznik> findByGradContainingIgnoreCase(String grad);

    List<Polaznik> findByStatusContainingIgnoreCase(String status);
}
