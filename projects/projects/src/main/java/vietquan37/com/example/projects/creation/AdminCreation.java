package vietquan37.com.example.projects.creation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.Role;
import vietquan37.com.example.projects.repository.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor

public class AdminCreation {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static boolean adminAccountCreated = false;


    public void createAdminAccount() {
        if (adminAccountCreated) {
            return;
        }

        if (userRepository.findByEmail("bubakush20099@gmail.com").isPresent()) {
            adminAccountCreated = true;
            return;
        }

        User adminUser = User.builder()
                .email("bubakush20099@gmail.com")
                .password(passwordEncoder.encode("123"))
                .fullName("Viet Quan")
                .role(Role.ADMIN)
                .address("Cali")
                .telephoneNumber("1234567890")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(adminUser);

        adminAccountCreated = true;
    }
}