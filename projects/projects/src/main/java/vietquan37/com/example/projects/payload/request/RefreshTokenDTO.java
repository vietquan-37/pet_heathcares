package vietquan37.com.example.projects.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenDTO {
    @NotBlank
    private String token;
}