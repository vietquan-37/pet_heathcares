package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.PetStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private Date admissionDate;
    private Date dischargeDate;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Cage cage;
    @Enumerated(EnumType.STRING)
    private PetStatus status;
    private BigDecimal totalPrice;
    @ManyToMany
    private List<Services>services;

}
