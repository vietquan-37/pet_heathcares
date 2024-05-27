package vietquan37.com.example.projects.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private Integer userId;
    private String accessToken;
    private String refreshToken;
    private Role role;

}
