package vietquan37.com.example.projects.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vietquan37.com.example.projects.enumClass.CageStatus;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CageResponse {
    private Integer id;
    private int cageNumber;
    private CageStatus cageStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private int capacity;
}
