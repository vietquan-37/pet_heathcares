package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.enumClass.ServiceTypes;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {
    Optional<Services> findByName(String name);
    List<Services> findAllByDeletedIsFalse();
    Optional<Services> findByIdAndDeletedIsFalseAndType(Integer id, ServiceTypes types);
    @Query("SELECT s FROM Services s WHERE s.deleted = false AND s.type = :type AND s.name <> 'Overnight Stay'")
    List<Services> findAllByDeletedIsFalseAndType(@Param("type") ServiceTypes types);



}
