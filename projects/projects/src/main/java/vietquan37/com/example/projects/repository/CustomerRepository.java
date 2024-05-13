package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

}
