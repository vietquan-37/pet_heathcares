package vietquan37.com.example.projects.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.CageDTO;
import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.service.ICageService;


@RestController
@RequestMapping("api/v1/cage")
@RequiredArgsConstructor
public class CageController {
private final ICageService cageService;
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> GetAllCage() {
        var response = cageService.getAllCage();
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());


    }
    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<APIResponse> GetAllCageForStaff() {
        var response = cageService.getAllCageForStaff();
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());


    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> GetAllById(@PathVariable Integer id) {
        var response =cageService.getCageById(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> CreateCage(@RequestBody @Valid CageDTO dto) throws UserMistake {
        cageService.addCage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data("Cage created successfully.")
                .build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> UpdateCage(@RequestBody @Valid CageDTO dto, @PathVariable Integer id) throws UserMistake {
       cageService.updateCage(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Cage updated successfully.")
                .build());
    }

    @PatchMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> DeleteCage(@PathVariable Integer id) {
       cageService.deleteCage(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Cage deleted successfully.")
                .build());


    }
}
