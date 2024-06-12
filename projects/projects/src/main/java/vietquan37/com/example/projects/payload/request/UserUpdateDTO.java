package vietquan37.com.example.projects.payload.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDTO {
    @NotBlank
    private String fullName;
    @NotBlank
    @Pattern(regexp = "\\d{10,11}", message = "Phone number must be 10 or 11 digits")
    @NotBlank
    private String phone;
    @NotBlank
    private String address;

}
