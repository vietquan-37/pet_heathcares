
package vietquan37.com.example.projects.payload.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;



@Data
@NoArgsConstructor
public class AppointmentDTO {
    @NotNull
    private LocalDate appointmentDate;
    @NotNull
    private Integer petId;
    @NotNull
    private Integer doctorId;
    @NotNull
    private Integer serviceId;

}
