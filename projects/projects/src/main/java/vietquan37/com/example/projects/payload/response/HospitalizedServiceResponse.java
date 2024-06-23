package vietquan37.com.example.projects.payload.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalizedServiceResponse {
    private Integer id;
    private String serviceName;
    private BigDecimal price;
    private LocalDate dateUsage;
}
