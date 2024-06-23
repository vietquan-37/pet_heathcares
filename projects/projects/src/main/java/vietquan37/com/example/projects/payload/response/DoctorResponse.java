package vietquan37.com.example.projects.payload.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.WorkingDay;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorResponse {
    private Integer id;
    private String fullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private String imageUrl;
    private String email;
    private String specialty;
    private List<WorkingDay> workingDay;
}
