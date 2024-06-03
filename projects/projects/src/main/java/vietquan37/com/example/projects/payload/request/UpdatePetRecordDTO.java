package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePetRecordDTO {
    @NotBlank
    private String dailyNote;

    @NotBlank
    private String diagnosis;
    @NotBlank
    private String treatment;
}
