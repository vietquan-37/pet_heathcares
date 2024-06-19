package vietquan37.com.example.projects.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> , PagingAndSortingRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Page<User>findAllByRoleIn(List<Role> roles,Pageable pageable);
    long countAllByRole(Role role);
}
