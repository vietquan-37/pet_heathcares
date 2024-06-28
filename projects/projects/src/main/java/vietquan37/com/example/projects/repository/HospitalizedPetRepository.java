package vietquan37.com.example.projects.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import vietquan37.com.example.projects.entity.HospitalizedPet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface HospitalizedPetRepository extends JpaRepository<HospitalizedPet, Integer>, PagingAndSortingRepository<HospitalizedPet,Integer> {
    int countHospitalizedPetByCageIdAndDischargeDateAndDeletedIsFalse(Integer id, LocalDate date);
   Optional<HospitalizedPet> findByIdAndDeletedIsFalse(Integer id);
    Optional<HospitalizedPet> findByPetIdAndDeletedIsFalseAndDischargeDate(Integer id,LocalDate date);
    List<HospitalizedPet> findAllByPetCustomerUserIdAndDeletedIsFalse(Integer userId);
    Page<HospitalizedPet>findAllByDeletedIsFalse(Pageable pageable);
    Page<HospitalizedPet>findAllByDeletedIsFalseAndDoctorId(Pageable pageable,Integer id);
    Optional<HospitalizedPet>  findByPaymentsPaymentId(String id);
    List<HospitalizedPet> findAllByPetIdAndDischargeDateIsNotNullAndDeletedIsFalse(Integer petId);
}
