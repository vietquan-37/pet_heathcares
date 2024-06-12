package vietquan37.com.example.projects.payload.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import vietquan37.com.example.projects.enumClass.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private boolean isDeleted;
    private BigDecimal balance;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
