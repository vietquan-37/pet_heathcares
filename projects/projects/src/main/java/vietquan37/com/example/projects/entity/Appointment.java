package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.enumClass.AppointmentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
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
    private LocalDateTime appointmentTime;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private AppointmentStatus appointmentStatus;
    private AppointmentType type;
    private BigDecimal appointmentPrice;
    private  double advance_payment;
    private double refund_payments;
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Review> reviews;


}
