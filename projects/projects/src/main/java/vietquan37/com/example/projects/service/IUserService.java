package vietquan37.com.example.projects.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import vietquan37.com.example.projects.exception.EmailAlreadyExistsException;
import vietquan37.com.example.projects.payload.request.UserDTO;
import vietquan37.com.example.projects.payload.request.UserUpdateDTO;
import vietquan37.com.example.projects.payload.response.UserResponse;



public interface IUserService {
Page<UserResponse> getAllUser(int page);
void createUser(UserDTO dto) throws EmailAlreadyExistsException;
void UpdateUser(UserUpdateDTO dto, Integer id) throws EmailAlreadyExistsException;
void deleteUser(Integer id);
UserResponse getUserById(Integer id);
}
