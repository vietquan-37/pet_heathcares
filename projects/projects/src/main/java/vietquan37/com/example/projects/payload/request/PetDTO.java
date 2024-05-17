package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import vietquan37.com.example.projects.enumClass.Gender;

import java.util.Date;

@Data
@NoArgsConstructor
public class PetDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String species;

    @NotNull
    private Gender gender;

    @NotNull
    private Date birthDate;

    private String imageUrl;


}
