package vietquan37.com.example.projects.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.entity.*;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.enumClass.ServiceType;
import vietquan37.com.example.projects.service.impl.AppointmentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AppointmentDataResponse {
    private Integer id;
    private LocalDate appointmentDate;
    private String customerName;
    private String petName;
    private String doctorName;
    private AppointmentStatus appointmentStatus;
    private BigDecimal appointmentPrice;
    private BigDecimal refund_payments;
    private boolean paidStatus;
    private ServiceType service;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
