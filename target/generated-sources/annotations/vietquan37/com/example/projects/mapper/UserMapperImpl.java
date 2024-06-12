package vietquan37.com.example.projects.mapper;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Customer;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.Role;
import vietquan37.com.example.projects.mapper.passwordMap.PasswordEncoderMapper;
import vietquan37.com.example.projects.payload.request.RegisterDTO;
import vietquan37.com.example.projects.payload.request.UserDTO;
import vietquan37.com.example.projects.payload.request.UserUpdateDTO;
import vietquan37.com.example.projects.payload.response.UserResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PasswordEncoderMapper passwordEncoderMapper;

    @Override
    public User mapRegisterRequestToUser(RegisterDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getUsername() );
        user.password( passwordEncoderMapper.encode( dto.getPassword() ) );
        user.fullName( dto.getFullName() );
        user.telephoneNumber( dto.getPhone() );
        user.address( dto.getAddress() );

        user.accountLocked( true );
        user.role( Role.USER );
        user.createdAt( java.time.LocalDateTime.now() );
        user.updatedAt( java.time.LocalDateTime.now() );

        return user.build();
    }

    @Override
    public UserResponse mapUserToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.role( user.getRole() );
        userResponse.name( user.getFullName() );
        userResponse.email( user.getEmail() );
        userResponse.password( user.getPassword() );
        userResponse.phoneNumber( user.getTelephoneNumber() );
        userResponse.address( user.getAddress() );
        userResponse.isDeleted( user.isAccountLocked() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );

        return userResponse.build();
    }

    @Override
    public UserResponse mapUserToResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.name( user.getFullName() );
        userResponse.email( user.getEmail() );
        userResponse.password( user.getPassword() );
        userResponse.phoneNumber( user.getTelephoneNumber() );
        userResponse.address( user.getAddress() );
        userResponse.role( user.getRole() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );

        return userResponse.build();
    }

    @Override
    public UserResponse mapCustomerResponse(Customer user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( userUserId( user ) );
        userResponse.name( userUserFullName( user ) );
        userResponse.email( userUserEmail( user ) );
        userResponse.password( userUserPassword( user ) );
        userResponse.phoneNumber( userUserTelephoneNumber( user ) );
        userResponse.address( userUserAddress( user ) );
        userResponse.balance( user.getCustomer_balance() );

        return userResponse.build();
    }

    @Override
    public User mapCreateRequest(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.role( dto.getRole() );
        user.email( dto.getUsername() );
        user.password( passwordEncoderMapper.encode( dto.getPassword() ) );
        user.telephoneNumber( dto.getPhone() );
        user.address( dto.getAddress() );
        user.fullName( dto.getFullName() );

        user.accountLocked( false );
        user.createdAt( java.time.LocalDateTime.now() );
        user.updatedAt( java.time.LocalDateTime.now() );

        return user.build();
    }

    @Override
    public void updateUserFromDto(UserUpdateDTO dto, User existingUser) {
        if ( dto == null ) {
            return;
        }

        existingUser.setAddress( dto.getAddress() );
        existingUser.setTelephoneNumber( dto.getPhone() );
        existingUser.setFullName( dto.getFullName() );

        existingUser.setUpdatedAt( java.time.LocalDateTime.now() );
    }

    private Integer userUserId(Customer customer) {
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        Integer id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String userUserFullName(Customer customer) {
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String fullName = user.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }

    private String userUserEmail(Customer customer) {
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String userUserPassword(Customer customer) {
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String password = user.getPassword();
        if ( password == null ) {
            return null;
        }
        return password;
    }

    private String userUserTelephoneNumber(Customer customer) {
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String telephoneNumber = user.getTelephoneNumber();
        if ( telephoneNumber == null ) {
            return null;
        }
        return telephoneNumber;
    }

    private String userUserAddress(Customer customer) {
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String address = user.getAddress();
        if ( address == null ) {
            return null;
        }
        return address;
    }
}
