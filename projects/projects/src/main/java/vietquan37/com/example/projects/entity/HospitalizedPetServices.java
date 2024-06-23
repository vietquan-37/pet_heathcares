package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hospitalized_pet_services")
public class HospitalizedPetServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "hospitalized_pet_id", nullable = false)
    private HospitalizedPet hospitalizedPet;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;

    @Column(name = "usage_date", nullable = false)
    private LocalDate usageDate;
}
