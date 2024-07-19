package vietquan37.com.example.projects.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vietquan37.com.example.projects.entity.HospitalizedPetServices;

public interface HospitalizedPetServiceRepository extends JpaRepository<HospitalizedPetServices,Integer> {
    Page<HospitalizedPetServices> findAllByHospitalizedPetIdOrderByUsageDateDesc(Integer hospitalizedPetId, Pageable pageable);
}

