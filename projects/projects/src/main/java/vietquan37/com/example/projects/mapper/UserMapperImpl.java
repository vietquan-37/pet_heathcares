package vietquan37.com.example.projects.mapper;

import javax.annotation.processing.Generated;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.Role;
import vietquan37.com.example.projects.payload.request.RegisterDTO;
import vietquan37.com.example.projects.payload.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> mapUsersToUserResponses(List<User> users) {
        return users.stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }
    @Override
    public User mapRegisterRequestToUser(RegisterDTO dto) {
        if (dto == null) {
            return null;
        }

        User.UserBuilder user = User.builder();
        user.fullName(dto.getFullName());
        user.email(dto.getUsername());
        user.password(passwordEncoder.encode(dto.getPassword()));
        user.telephoneNumber(dto.getPhone());
        user.address(dto.getAddress());
        user.user_role(Role.USER);

        return user.build();
    }

    @Override
    public UserResponse mapUserToUserResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();
        userResponse.id(user.getId());
        userResponse.name(user.getFullName());
        userResponse.email(user.getEmail());
        userResponse.password(user.getPassword());
        userResponse.phoneNumber(user.getTelephoneNumber());
        userResponse.address(user.getAddress());
        userResponse.isDeleted(user.isAccountLocked());
        userResponse.role(user.getUser_role());
        return userResponse.build();
    }
}
