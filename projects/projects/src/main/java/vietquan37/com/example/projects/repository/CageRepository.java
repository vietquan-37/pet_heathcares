package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vietquan37.com.example.projects.entity.Cage;
import vietquan37.com.example.projects.enumClass.CageStatus;

import java.util.List;
import java.util.Optional;

public interface CageRepository extends JpaRepository<Cage, Integer> {
    Cage findByCageNumber(int cageNumber);

    List<Cage> findAllByDeletedIsFalseAndCageStatus(CageStatus cageStatus);

    Optional<Cage> findByIdAndDeletedIsFalseAndCageStatus(int id, CageStatus cageStatus);

}
