package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.*;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.enumClass.AppointmentType;
import vietquan37.com.example.projects.utils.converter.AppointmentTypeConverter;


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
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private AppointmentStatus appointmentStatus;
    private BigDecimal appointmentPrice;
    private BigDecimal refund_payments;
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL)
    private List<Review> reviews;
    private boolean paidStatus;
    @Convert(converter = AppointmentTypeConverter.class)
    private List<AppointmentType> appointmentTypes;


}
