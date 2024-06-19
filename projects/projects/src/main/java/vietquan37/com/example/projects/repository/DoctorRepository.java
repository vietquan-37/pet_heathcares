package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.enumClass.WorkingDay;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
   Optional< Doctor> findByUser_Id(Integer userId);
   List<Doctor> findAllByUser_AccountLockedFalse();
   @Query("SELECT d FROM Doctor d WHERE CONCAT(',', d.workingDay, ',') LIKE CONCAT('%,', :workingDay, ',%') AND d.user.accountLocked = false")
   List<Doctor> findAllBySpecificWorkingDayAndUser_AccountLockedFalse(@Param("workingDay") String workingDay);
}
