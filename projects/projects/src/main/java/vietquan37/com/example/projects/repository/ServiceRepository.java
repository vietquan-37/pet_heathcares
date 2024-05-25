package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {
    Optional<Services> findByName(String name);
    List<Services> findAllByDeletedIsFalse();

}
