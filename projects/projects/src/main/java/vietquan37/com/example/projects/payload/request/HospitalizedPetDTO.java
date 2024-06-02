package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import vietquan37.com.example.projects.enumClass.PetStatus;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor

public class HospitalizedPetDTO {
    @NotBlank
    private String dailyNote;
    @NotBlank
    private String diagnosis;
    @NotBlank
    private String treatment;
    @NotNull
    private Integer petId;
    @NotNull
    private Date admissionDate;
    @NotNull
    private Integer doctorId;
    @NotNull
    private Integer cageId;
    @Size(min = 1)
    private List<Integer> serviceIds;
}