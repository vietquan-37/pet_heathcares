package vietquan37.com.example.projects.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.DailyNote;

import java.time.LocalDate;

@Repository
public interface DailyNoteRepository extends JpaRepository<DailyNote, Integer> {
    DailyNote findByHospitalizedPetIdAndDate(Integer id,LocalDate date);

    Page<DailyNote> findAllByHospitalizedPetIdOrderByDateDesc(Integer id,Pageable pageable);
}
