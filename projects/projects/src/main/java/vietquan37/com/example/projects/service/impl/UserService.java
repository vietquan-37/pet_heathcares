package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.entity.Customer;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.Role;
import vietquan37.com.example.projects.exception.EmailAlreadyExistsException;
import vietquan37.com.example.projects.mapper.UserMapper;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.request.UserDTO;
import vietquan37.com.example.projects.payload.request.UserUpdateDTO;
import vietquan37.com.example.projects.payload.response.UserResponse;
import vietquan37.com.example.projects.repository.CustomerRepository;
import vietquan37.com.example.projects.repository.DoctorRepository;
import vietquan37.com.example.projects.repository.UserRepository;
import vietquan37.com.example.projects.service.IUserService;


import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomerRepository customerRepository;
    private final DoctorRepository doctorRepository;
private final int MAX=5;

    @Override
    public void createUser(UserDTO dto) throws EmailAlreadyExistsException {
        String email = dto.getUsername();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email address already registered");
        } else {
            User user = userMapper.mapCreateRequest(dto);

            userRepository.save(user);
            if (dto.getRole() == Role.DOCTOR) {
                Doctor doctor = new Doctor();
                doctor.setUser(user);
                doctorRepository.save(doctor);
            } else if (dto.getRole() == Role.USER) {
                Customer customer = new Customer();
                customer.setCustomer_balance(0);
                customer.setUser(user);
                customerRepository.save(customer);
            }

        }
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUpdatedAt(LocalDateTime.now());
        user.setAccountLocked(true);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.mapUserToResponse(user);
    }



    @Override
    public void UpdateUser(UserUpdateDTO dto, Integer id) throws EntityNotFoundException, EmailAlreadyExistsException {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getEmail().equals(dto.getUsername()) && userRepository.findByEmail(dto.getUsername()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        userMapper.updateUserFromDto(dto,user);
        userRepository.save(user);
    }



    @Override
    public Page<UserResponse> getAllUser(int page) {
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<User> userPage = userRepository.findAllByRoleIn(List.of(Role.USER, Role.DOCTOR, Role.STAFF), pageable);
        return userPage.map(userMapper::mapUserToUserResponse);
    }

}
