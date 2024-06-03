package vietquan37.com.example.projects.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.PetStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalizedPetResponse {
    private Integer id;
    private String dailyNote;
    private String diagnosis;
    private String treatment;
    private String petName;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private String doctorName;
    private int cageNumber;
    private PetStatus status;
    private BigDecimal totalPrice;
    private boolean paid;
}
