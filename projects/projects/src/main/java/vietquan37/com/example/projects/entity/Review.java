package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
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
