package vietquan37.com.example.projects.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Review;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>, PagingAndSortingRepository<Review,Integer> {
    Page<Review> findAll(Pageable pageable);
    List<Review> findAllByCustomerId(Integer customerId);
    Review findByAppointmentId(Integer appointmentId);

}
