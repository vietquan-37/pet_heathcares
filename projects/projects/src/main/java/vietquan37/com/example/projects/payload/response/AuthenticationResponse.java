package vietquan37.com.example.projects.payload.response;

import lombok.*;
import vietquan37.com.example.projects.enumClass.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private Integer userId;
    private String accessToken;
    private String refreshToken;
    private Role role;

}
