package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class ServiceUpdateDTO {
    @NotNull
    @Max(value = 10000)
    @Min(value = 1)
    private BigDecimal price;
    @NotBlank
    private String name;

}
