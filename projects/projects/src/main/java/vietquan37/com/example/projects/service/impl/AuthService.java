package vietquan37.com.example.projects.service.impl;

import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vietquan37.com.example.projects.config.JwtService;
import vietquan37.com.example.projects.entity.Customer;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.entity.VerificationToken;
import vietquan37.com.example.projects.exception.EmailAlreadyExistsException;
import vietquan37.com.example.projects.mapper.UserMapper;
import vietquan37.com.example.projects.payload.request.LoginDTO;
import vietquan37.com.example.projects.payload.request.RefreshTokenDTO;
import vietquan37.com.example.projects.payload.request.RegisterDTO;
import vietquan37.com.example.projects.payload.response.AuthenticationResponse;
import vietquan37.com.example.projects.repository.CustomerRepository;
import vietquan37.com.example.projects.repository.UserRepository;
import vietquan37.com.example.projects.repository.VerificationTokenRepository;
import vietquan37.com.example.projects.service.IAuthService;
import vietquan37.com.example.projects.email.EmailService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final EmailService emailService;
    private final VerificationTokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    private static final String VERIFICATION_URL = "http://localhost:8080/api/v1/auth/verify-email?token=";

    @Override
    public void register(RegisterDTO registerDTO) throws EmailAlreadyExistsException, MessagingException, UnsupportedEncodingException {
        User user = userMapper.mapRegisterRequestToUser(registerDTO);
        if (userRepository.findByEmail(registerDTO.getUsername()).isPresent()) {
            throw new EmailAlreadyExistsException("Email address already registered");
        }

        userRepository.save(user);
        String token = generateAndSaveVerifyToken(user);
        var customer = new Customer();
        customer.setCustomer_balance(0);
        customer.setUser(user);
        customerRepository.save(customer);
        emailService.sendVerificationEmail(VERIFICATION_URL + token, user.getEmail());
    }


    @Override
    public void VerifyEmail(String token) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (LocalDateTime.now().isAfter(verificationToken.getExpiresAt())) {
            emailService.sendVerificationEmail(VERIFICATION_URL + token, verificationToken.getUser().getEmail());
            throw new RuntimeException("The verification link has expired. A new link has been sent to your email. Please check your email.");
        }

        User user = userRepository.findById(verificationToken.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountLocked(false);
        userRepository.save(user);

    }

    @Override
    public AuthenticationResponse authenticate(LoginDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
                //this trigger the authenticationProvider() to authenticate user
        );
        var user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).userId(user.getId()).build();
    }

    private String generateAndSaveVerifyToken(User user) {
        String generatedToken = UUID.randomUUID().toString();
        VerificationToken token = VerificationToken.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenDTO dto) {
        String userEmail = jwtService.extractUsername(dto.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        var accessToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().userId(user.getId()).accessToken(accessToken).refreshToken(dto.getToken()).build();
    }

}
