package vietquan37.com.example.projects.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalizedServiceResponse {
    private Integer id;
    private String serviceName;
    private BigDecimal price;
    private LocalDate dateUsage;
}
