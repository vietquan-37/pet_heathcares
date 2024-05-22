package vietquan37.com.example.projects.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vietquan37.com.example.projects.exception.FileException;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.payload.request.PetDTO;
import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.service.IPetService;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/pet")
@RequiredArgsConstructor
public class PetController {
    @Autowired
    private final IPetService petService;

    @PostMapping( "/create" )
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> create(@Valid @RequestBody PetDTO dto, Authentication connectedUser) {
        petService.CreatePet(dto, connectedUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.builder().status(HttpStatus.CREATED.value())
                        .data("Pet created successfully").build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<APIResponse> getAllPet(@RequestParam(defaultValue = "0") int page) {
        var response=petService.GetAllPets(page);
        return ResponseEntity
                .ok(APIResponse.builder().data(response)
                        .status(HttpStatus.OK.value()).build());
    }
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> getAllPetByUser( Authentication connectedUser) {
       var response=petService.GetAllPetsByUser(connectedUser);
        return ResponseEntity
                .ok(APIResponse.builder().data(response)
                        .status(HttpStatus.OK.value()).build());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<APIResponse> getUserPetById(@PathVariable Integer id, Authentication connectedUser) throws OperationNotPermittedException {
        var response=petService.GetUserPetById(id,connectedUser);
        return ResponseEntity
                .ok(APIResponse.builder().data(response)
                        .status(HttpStatus.OK.value()).build());
    }
    @PutMapping( "/update/{petId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> UpdatePet(@PathVariable Integer petId, @Valid @RequestBody PetDTO dto, Authentication connectedUser) throws OperationNotPermittedException {
        petService.UpdatePet(petId,dto,connectedUser);
        return ResponseEntity.ok( APIResponse.builder().status(HttpStatus.OK.value())
                .data("Pet updated successfully").build());

    }
    @PatchMapping("/delete/{petId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> DeletePet(@PathVariable Integer petId, Authentication connectedUser) throws OperationNotPermittedException {
        petService.DeletePet(petId, connectedUser);
        return ResponseEntity.ok( APIResponse.builder().status(HttpStatus.OK.value())
                .data("Pet deleted successfully").build());

    }
    @PostMapping(value = "/image/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> uploadPetImage(
            @PathVariable Integer id,
            Authentication connectedUser,
            @Parameter()
            @RequestPart("file") MultipartFile file
    ) throws FileException, IOException, OperationNotPermittedException {
       petService.UploadImage(id,file,connectedUser);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Image uploaded successfully.")
                .build());
    }


}
