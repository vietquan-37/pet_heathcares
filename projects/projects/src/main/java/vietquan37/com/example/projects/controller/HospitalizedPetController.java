package vietquan37.com.example.projects.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.service.IHospitalizedPetService;

@RestController
@RequestMapping("api/v1/petCare")
@RequiredArgsConstructor
public class HospitalizedPetController {
    private final IHospitalizedPetService hospitalizedPetService;
    @PostMapping("/create")
    public ResponseEntity<APIResponse> create(@RequestBody@ Valid HospitalizedPetDTO dto) {
        hospitalizedPetService.addHospitalizedPet(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Hospitalized pet created successfully").build());

    }
}
