
package vietquan37.com.example.projects.payload.request;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vietquan37.com.example.projects.enumClass.TimeFrame;

import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor
public class AppointmentDTO {
    @NotNull
    private LocalDate appointmentDate;
    @NotNull
    private Integer petId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TimeFrame timeFrame;
    @NotNull
    private Integer doctorId;
    @NotNull
    private Integer serviceId;

}
