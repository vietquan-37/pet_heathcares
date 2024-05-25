package vietquan37.com.example.projects.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.mapper.passwordMap.EncodedMapping;
import vietquan37.com.example.projects.mapper.passwordMap.PasswordEncoderMapper;
import vietquan37.com.example.projects.payload.request.RegisterDTO;
import vietquan37.com.example.projects.payload.request.UserDTO;
import vietquan37.com.example.projects.payload.request.UserUpdateDTO;
import vietquan37.com.example.projects.payload.response.UserResponse;


@Mapper(componentModel = "spring",uses= PasswordEncoderMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "username")
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "telephoneNumber", source = "phone")
    @Mapping(target = "accountLocked", constant = "true")
    @Mapping(target = "role",constant = "USER")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    User mapRegisterRequestToUser(RegisterDTO dto);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "name", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phoneNumber", source = "telephoneNumber")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "isDeleted", source = "accountLocked")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    UserResponse mapUserToUserResponse(User user);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phoneNumber", source = "telephoneNumber")
    @Mapping(target = "address", source = "address")
    UserResponse mapUserToResponse(User user);




    @Mapping(target = "role", source = "role")
    @Mapping(target = "email", source = "username")
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "telephoneNumber", source = "phone")
    @Mapping(target = "accountLocked", constant = "false")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "address", source = "address")
    User mapCreateRequest(UserDTO dto);


    @Mapping(target = "email", source = "username")
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    @Mapping(target = "address", source = "address")
    @Mapping(target = "telephoneNumber", source = "phone")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget User existingUser);


}
