package vietquan37.com.example.projects.enumClass;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public enum Role {
    ADMIN,
    USER,
    STAFF,
    VETERINARIAN;
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

