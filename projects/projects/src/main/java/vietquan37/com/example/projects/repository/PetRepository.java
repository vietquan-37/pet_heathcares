package vietquan37.com.example.projects.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Customer;
import vietquan37.com.example.projects.entity.Pet;

import java.util.List;


@Repository
public interface PetRepository extends JpaRepository<Pet, Integer>, PagingAndSortingRepository<Pet, Integer> {

    Page<Pet> findAllByDeletedIsFalse(Pageable pageable);
    List<Pet>findAllByCustomerAndDeletedIsFalse(Customer customer);

}
