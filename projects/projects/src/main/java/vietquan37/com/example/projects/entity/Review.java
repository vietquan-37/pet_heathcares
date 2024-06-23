package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;
    private int rating;
    @ManyToOne
    private Customer customer;
    @OneToOne(mappedBy = "review")
    private Appointment appointment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
