package vietquan37.com.example.projects.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.mapper.UserMapper;
import vietquan37.com.example.projects.payload.response.UserResponse;
import vietquan37.com.example.projects.repository.UserRepository;
import vietquan37.com.example.projects.service.IUserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserResponse> getAllUser() {
     var user= userRepository.findAll();
     return userMapper.mapUsersToUserResponses(user);


    }
}
