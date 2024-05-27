package vietquan37.com.example.projects.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vietquan37.com.example.projects.enumClass.TokenType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}