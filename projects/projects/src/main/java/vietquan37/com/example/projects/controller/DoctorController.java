package vietquan37.com.example.projects.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vietquan37.com.example.projects.enumClass.TimeFrame;
import vietquan37.com.example.projects.exception.FileException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.request.DoctorSearchDTO;
import vietquan37.com.example.projects.payload.response.APIResponse;

import vietquan37.com.example.projects.service.IDoctorService;

import java.io.IOException;
import java.time.LocalDate;


@RestController
@RequestMapping("api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final IDoctorService doctorService;
    @PutMapping("/doctorUpdate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> UpdateDoctor(@RequestBody @Valid DoctorDTO dto, @PathVariable Integer id)  {
        doctorService.UpdateDoctor(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Doctor updated successfully.")
                .build());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<APIResponse> GetAllDoctorById(@PathVariable Integer id) {
        var response=doctorService.GetDoctorById(id);
        return ResponseEntity.ok(APIResponse.builder()
                .data(response).status(HttpStatus.OK.value()).build());
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> GetAllDoctorsForAdmin() {
        var response=doctorService.GetAllDoctorForAdmin();
        return ResponseEntity.ok(APIResponse.builder()
                .data(response).status(HttpStatus.OK.value()).build());
    }
    @GetMapping
    public ResponseEntity<APIResponse> GetAllDoctors() {
        var response=doctorService.GetAllDoctors();
        return ResponseEntity.ok(APIResponse.builder()
                .data(response).status(HttpStatus.OK.value()).build());
    }
    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<APIResponse> GetAllDoctorInfo(Authentication authentication) {
        var response=doctorService.GetDoctorInfo(authentication);
        return ResponseEntity.ok(APIResponse.builder()
                .data(response).status(HttpStatus.OK.value()).build());
    }
    @GetMapping("/availability")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> GetAllDoctorAvailability(@RequestParam LocalDate date, @RequestParam TimeFrame timeFrame) throws UserMistake {
        var response=doctorService.GetDoctorAvailability(date, timeFrame);
        return ResponseEntity.ok(APIResponse.builder()
                .data(response).status(HttpStatus.OK.value()).build());
    }
    @PostMapping(value = "/image/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<?> uploadDoctorImage(
            @PathVariable Integer id,
            @Parameter()
            @RequestPart("file") MultipartFile file
    ) throws FileException, IOException {
doctorService.UploadImage(id,file);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Image uploaded successfully.")
                .build());
    }
}
