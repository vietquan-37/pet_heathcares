package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.enumClass.TimeFrame;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private LocalDate appointmentDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @Enumerated(EnumType.STRING)
    private TimeFrame timeFrame;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    private BigDecimal appointmentPrice;
    private BigDecimal refund_payments;
    @OneToOne
    private Review review;
    @ManyToOne
    @JoinColumn(name = "services_id")
    private Services service;
    @OneToOne
    private Payments payments;


}
