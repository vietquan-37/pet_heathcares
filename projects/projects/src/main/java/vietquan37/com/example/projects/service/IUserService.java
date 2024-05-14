package vietquan37.com.example.projects.service;

import vietquan37.com.example.projects.exception.EmailAlreadyExistsException;
import vietquan37.com.example.projects.payload.request.UserDTO;
import vietquan37.com.example.projects.payload.response.UserResponse;

import java.util.List;

public interface IUserService {
List<UserResponse> getAllUser();
void createUser(UserDTO dto) throws EmailAlreadyExistsException;
void UpdateUser(UserDTO dto,Integer id) throws EmailAlreadyExistsException;
}
