package vietquan37.com.example.projects.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vietquan37.com.example.projects.exception.EmailAlreadyExistsException;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.request.UserDTO;
import vietquan37.com.example.projects.payload.request.UserUpdateDTO;
import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.service.IUserService;


@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final IUserService UserService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> GetAllUsers(@RequestParam(defaultValue = "0")int page) {
        var response = UserService.getAllUser(page);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());


    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> GetAllById(@PathVariable Integer id) {
        var response = UserService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> CreateUser(@RequestBody @Valid UserDTO dto) throws EmailAlreadyExistsException {
        UserService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data("User registered successfully.")
                .build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> UpdateUser(@RequestBody @Valid UserUpdateDTO dto, @PathVariable Integer id) throws EmailAlreadyExistsException {
        UserService.UpdateUser(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("User updated successfully.")
                .build());
    }

    @PatchMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> DeleteUser(@PathVariable Integer id) {
        UserService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("User deleted successfully.")
                .build());


    }
}
