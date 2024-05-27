package vietquan37.com.example.projects.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vietquan37.com.example.projects.exception.EmailAlreadyExistsException;
import vietquan37.com.example.projects.exception.MisMatchPassword;
import vietquan37.com.example.projects.payload.request.*;
import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.service.IAuthService;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://localhost:3000")
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(@RequestBody @Valid RegisterDTO registerDTO) throws MessagingException, EmailAlreadyExistsException, UnsupportedEncodingException {
        authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data("User registered successfully.")
                .build());
    }

    @GetMapping("/verify-email")
    public ResponseEntity<APIResponse> verifyEmail(@RequestParam String token) throws MessagingException, UnsupportedEncodingException {
        authService.VerifyEmail(token);
        return ResponseEntity.ok(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Email verified successfully.")
                .build());
    }
    @PostMapping("/authenticate")
    public ResponseEntity<APIResponse> authenticate(@RequestBody @Valid LoginDTO request){
       var result= authService.authenticate(request);
       return  ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data(result).build());
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<APIResponse> refreshToken(
          @Valid  @RequestBody RefreshTokenDTO request
    ) {
       return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value())
               .data(authService.refreshToken(request)).build());
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<APIResponse> forgotPassword(
            @Valid  @RequestBody ForgotPasswordDTO request
    ) throws MessagingException, UnsupportedEncodingException {
        authService.ForgotPassword(request);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data("send forgot password email successfully").build());
    }
    @PostMapping("/reset-password")
    public ResponseEntity<APIResponse> resetPassword(
            @RequestParam String token,     @Valid  @RequestBody ResetPasswordDTO request
    ) throws MessagingException, UnsupportedEncodingException, MisMatchPassword {
        authService.resetPassword(token,request);
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data("reset password successfully").build());
    }
}
