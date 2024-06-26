package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO {
    @NotBlank
    private String fullName;
    @Email
    @NotBlank
    private String username;
    @NotBlank
    @Size(min=2,max = 16)
    private String password;
    @Pattern(regexp="\\d{10,11}", message="Phone number must be 10 or 11 digits")
    @NotBlank
    private String phone;
    @NotBlank
    private String address;


}
