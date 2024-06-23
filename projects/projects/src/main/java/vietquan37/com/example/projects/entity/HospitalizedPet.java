package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;
import vietquan37.com.example.projects.enumClass.PetStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;

@Entity
@Getter
@Setter
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
    @OneToMany(mappedBy = "hospitalizedPet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HospitalizedPetServices> hospitalizedPetServices;
    private boolean deleted;
    private boolean paid;
    @OneToOne
    private Payments payments;
}
