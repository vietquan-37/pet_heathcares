package vietquan37.com.example.projects.mapper;

import org.mapstruct.Mapper;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.payload.request.RegisterDTO;
import vietquan37.com.example.projects.payload.response.UserResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapRegisterRequestToUser(RegisterDTO dto);
    UserResponse mapUserToUserResponse(User user);
    List<UserResponse> mapUsersToUserResponses(List<User> users);
}
