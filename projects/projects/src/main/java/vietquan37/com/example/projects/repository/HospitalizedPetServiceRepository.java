package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vietquan37.com.example.projects.entity.HospitalizedPetServices;

public interface HospitalizedPetServiceRepository extends JpaRepository<HospitalizedPetServices,Integer> {
}
