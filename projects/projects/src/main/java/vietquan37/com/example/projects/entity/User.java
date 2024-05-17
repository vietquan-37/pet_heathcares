package vietquan37.com.example.projects.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vietquan37.com.example.projects.enumClass.Role;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tbl_users"
)
@Entity
@Builder
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(
            unique = true
    )
    private String email;
    private String password;
    private String telephoneNumber;
    private String address;
    private boolean accountLocked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getName() {
        return email;
    }
}
