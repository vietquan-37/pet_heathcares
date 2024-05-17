package vietquan37.com.example.projects.payload.response;

import lombok.Builder;
import lombok.Data;
import vietquan37.com.example.projects.enumClass.Role;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private boolean isDeleted;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
