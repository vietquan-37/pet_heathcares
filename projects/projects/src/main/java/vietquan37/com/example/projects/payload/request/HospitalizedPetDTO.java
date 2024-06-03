package vietquan37.com.example.projects.payload.request;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class HospitalizedPetDTO {

    @NotNull
    private Integer petId;
    @NotNull
    private Integer doctorId;
    @NotNull
    private Integer cageId;
    @Size(min = 1)
    private List<Integer> serviceIds;
}