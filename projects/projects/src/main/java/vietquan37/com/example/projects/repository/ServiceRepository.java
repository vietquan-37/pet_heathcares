package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Service;
import java.math.BigDecimal;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
