package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;
import vietquan37.com.example.projects.enumClass.CageStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private int cageNumber;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private CageStatus cageStatus;
    @OneToMany(mappedBy = "cage", cascade = CascadeType.ALL)
    private List<HospitalizedPet> hospitalizations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
}
