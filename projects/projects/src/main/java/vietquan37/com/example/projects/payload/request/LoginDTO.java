package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
public class LoginDTO {
    @Email
    @NotBlank
    private String username;

    @NotBlank
    @Size(min=2,max = 16)
    private String password;
}
