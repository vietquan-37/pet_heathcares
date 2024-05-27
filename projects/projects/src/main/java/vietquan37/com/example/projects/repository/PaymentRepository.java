package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vietquan37.com.example.projects.entity.Payments;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payments, Integer> {
    Optional<Payments> findByPaymentId(String paymentId);
}
