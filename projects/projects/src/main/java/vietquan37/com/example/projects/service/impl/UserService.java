package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.entity.Customer;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.Role;
import vietquan37.com.example.projects.exception.EmailAlreadyExistsException;
import vietquan37.com.example.projects.exception.MisMatchPassword;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.UserMapper;
import vietquan37.com.example.projects.payload.request.ChangePasswordDTO;
import vietquan37.com.example.projects.payload.request.UserDTO;
import vietquan37.com.example.projects.payload.request.UserUpdateDTO;
import vietquan37.com.example.projects.payload.response.DashboardResponse;
import vietquan37.com.example.projects.payload.response.UserResponse;
import vietquan37.com.example.projects.repository.CustomerRepository;
import vietquan37.com.example.projects.repository.DoctorRepository;
import vietquan37.com.example.projects.repository.PetRepository;
import vietquan37.com.example.projects.repository.UserRepository;
import vietquan37.com.example.projects.service.IUserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomerRepository customerRepository;
    private final DoctorRepository doctorRepository;
    private  static final int MAX = 5;
    private final PasswordEncoder encoder;
    private final PetRepository petRepository;

    @Override
    public void changePassword(ChangePasswordDTO dto, Authentication authentication) throws UserMistake, MisMatchPassword {
        User user = ((User) authentication.getPrincipal());
        if (!encoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new UserMistake("wrong old password");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new MisMatchPassword("password is unmatched");
        }
        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);

    }

    @Override
    public void unDeleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUpdatedAt(LocalDateTime.now());
        user.setAccountLocked(false);
        userRepository.save(user);
    }

    @Override
    public DashboardResponse dashboard() {
       var totalCustomer= customerRepository.count();
       var totalDoctor= doctorRepository.count();
       var totalStaff= userRepository.countAllByRole(Role.STAFF);
       var totalPet=petRepository.count();


        return DashboardResponse.builder().totalCustomer(totalCustomer).totalDoctor(totalDoctor).totalPet(totalPet).totalStaff(totalStaff).build();
    }

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
                customer.setCustomer_balance(BigDecimal.ZERO);
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
    public UserResponse getCustomerInfo(Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        var customer = customerRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        return userMapper.mapCustomerResponse(customer);
    }




    @Override
    public void UpdateUser(UserUpdateDTO dto, Integer id) throws EntityNotFoundException{
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
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
