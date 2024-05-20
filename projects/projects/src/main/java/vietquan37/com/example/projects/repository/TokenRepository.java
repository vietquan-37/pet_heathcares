package vietquan37.com.example.projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Token;
import vietquan37.com.example.projects.enumClass.TokenType;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByTokenAndType(String token, TokenType type);
}
