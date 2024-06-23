package vietquan37.com.example.projects.payload.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import vietquan37.com.example.projects.custom.UniqueElements;
import vietquan37.com.example.projects.enumClass.WorkingDay;
import java.util.List;

@Data
@NoArgsConstructor
public class   DoctorDTO {
    @NotBlank
    private String specialty;
    @Size(min = 4, max = 6, message = "Working days must contain at least 4 days and max 6 days")
    @UniqueElements(message = "Working days must be unique")
    private List<WorkingDay> workingDay;

}
