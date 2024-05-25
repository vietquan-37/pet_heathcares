package vietquan37.com.example.projects.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;

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
    private String service;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
