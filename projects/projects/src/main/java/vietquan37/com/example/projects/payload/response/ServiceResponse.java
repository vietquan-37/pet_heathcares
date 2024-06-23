package vietquan37.com.example.projects.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vietquan37.com.example.projects.enumClass.ServiceTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {
    private Integer id;
    private String name;
    private BigDecimal price;
    private ServiceTypes type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
}
