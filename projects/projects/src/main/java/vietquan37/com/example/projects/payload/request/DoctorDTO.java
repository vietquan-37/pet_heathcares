package vietquan37.com.example.projects.payload.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import vietquan37.com.example.projects.custom.UniqueElements;
import vietquan37.com.example.projects.enumClass.WorkingDay;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
public class   DoctorDTO {
    @NotBlank
    private String specialty;
    @NotNull
    private LocalTime start_time;
    @NotNull
    private LocalTime end_time;
    @Size(min = 6, message = "Working days must contain at least 6 days")
    @UniqueElements(message = "Working days must be unique")
    private List<WorkingDay> workingDay;

}
