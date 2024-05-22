package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Doctor;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
   Optional< Doctor> findByUser_Id(Integer userId);
}
