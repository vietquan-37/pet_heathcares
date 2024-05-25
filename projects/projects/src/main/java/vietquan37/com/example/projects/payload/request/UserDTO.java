package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import vietquan37.com.example.projects.enumClass.Role;

@Data
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String fullName;
    @Email
    @NotBlank
    private String username;
    @NotBlank
    @Size(min=2,max = 16)
    private String password;
    @Pattern(regexp = "\\d{10,11}", message = "Phone number must be 10 or 11 digits")
    @NotBlank
    private String phone;
    @NotBlank
    private String address;
    @NotNull
    private Role role;

}
