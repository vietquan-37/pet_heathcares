package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.PetStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalizedPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String dailyNote;
    private String diagnosis;
    private String treatment;
    @ManyToOne
    private Pet pet;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Cage cage;
    @Enumerated(EnumType.STRING)
    private PetStatus status;
    private BigDecimal totalPrice;

    @ManyToMany
    @JoinTable(
            name = "hospitalized_pet_services",
            joinColumns = @JoinColumn(name = "hospitalized_pet_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Services> services;
    @ElementCollection
    @CollectionTable(
            name = "hospitalized_pet_services",
            joinColumns = @JoinColumn(name = "hospitalized_pet_id")
    )
    @MapKeyJoinColumn(name = "service_id")
    @Column(name = "service_usage")
    private Map<Services, LocalDate> serviceUsage;
    private boolean deleted;

}
