package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDTO {
    @NotBlank
    @Size(min=2,max = 16)
    private String oldPassword;
    @Size(min=2,max = 16)
    @NotBlank
    private String newPassword;
    @NotBlank
    private String confirmPassword;
}
