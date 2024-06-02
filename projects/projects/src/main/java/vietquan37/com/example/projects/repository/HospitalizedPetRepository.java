package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import vietquan37.com.example.projects.entity.HospitalizedPet;
import vietquan37.com.example.projects.enumClass.CageStatus;

import java.time.LocalDate;


public interface HospitalizedPetRepository extends JpaRepository<HospitalizedPet, Integer>, PagingAndSortingRepository<HospitalizedPet,Integer> {
    int countHospitalizedPetByCageIdAndDischargeDate(Integer id, LocalDate date);
}
