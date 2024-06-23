package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vietquan37.com.example.projects.enumClass.Gender;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PetDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String species;
    @NotNull
    private Gender gender;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;



}
