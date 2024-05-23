package vietquan37.com.example.projects.advice;

import com.paypal.base.rest.PayPalRESTException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;
import vietquan37.com.example.projects.exception.*;
import vietquan37.com.example.projects.payload.response.APIResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<APIResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(errors)
                .build());
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<APIResponse> handleLockedException(LockedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(APIResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("User account is locked")
                .build());
    }
    @ExceptionHandler(UserMistake.class)
    public ResponseEntity<APIResponse> handleUserMistake(UserMistake ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .build());
    }
    @ExceptionHandler(PayPalRESTException.class)
    public ResponseEntity<APIResponse> handlePayPalRESTException(PayPalRESTException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("PayPal REST Exception: " + ex.getMessage())
                .build());
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<APIResponse> handleFile(FileException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse> handleNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(APIResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getMessage())
                .build());
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<APIResponse> handleOperationNotPermittedException(OperationNotPermittedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(ex.getMessage())
                .build());
    }

    @ExceptionHandler(DoctorNotAvailableException.class)
    public ResponseEntity<APIResponse> handleDoctorNotAvailableException(DoctorNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .build());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<APIResponse> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(APIResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("JWT token has expired")
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(ex.getClass().getName() + ": " + ex.getMessage())
                .build());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error("Access Denied")
                .build());
    }
    @ExceptionHandler(MisMatchPassword.class)
    public ResponseEntity<APIResponse> handleMisMatchPassword(MisMatchPassword ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .build());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(APIResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Invalid username or password")
                .build());
    }

}
