package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordDTO {
    @NotBlank
    @Size(min=2,max = 16)
    private String password;
    @NotBlank
    @Size(min=2,max = 16)
    private String confirmPassword;
}
