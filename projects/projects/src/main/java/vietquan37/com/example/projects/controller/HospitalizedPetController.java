package vietquan37.com.example.projects.controller;

import com.paypal.base.rest.PayPalRESTException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetRecordDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetServiceDTO;
import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.payload.response.HospitalizedPetResponse;
import vietquan37.com.example.projects.payload.response.HospitalizedServiceResponse;
import vietquan37.com.example.projects.payload.response.PaymentResponse;
import vietquan37.com.example.projects.service.IHospitalizedPetService;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("api/v1/petCare")
@RequiredArgsConstructor
public class HospitalizedPetController {
    private final IHospitalizedPetService hospitalizedPetService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<APIResponse> create(@RequestBody @Valid HospitalizedPetDTO dto) throws UserMistake {
        hospitalizedPetService.addHospitalizedPet(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data("Hospitalized pet created successfully").build());
    }

    @PostMapping("/updateService/{id}")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<APIResponse> updateServiceForPet(@PathVariable Integer id, @RequestBody @Valid UpdatePetServiceDTO dto) throws OperationNotPermittedException {
        hospitalizedPetService.updateServiceForPet(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Service updated successfully").build());
    }

    @DeleteMapping("/deleteService/{id}")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<APIResponse> deleteServiceForPet(@PathVariable Integer id) throws OperationNotPermittedException {
        hospitalizedPetService.deleteServiceForPet(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Service deleted successfully").build());
    }

    @GetMapping("/doctor/all")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<APIResponse> getAllForDoctor(@RequestParam (defaultValue = "0")int page, Authentication authentication) {
        Page<HospitalizedPetResponse> responses = hospitalizedPetService.getAllForDoctor(page, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responses).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'DOCTOR')")
    public ResponseEntity<APIResponse> getById(@PathVariable Integer id) {
        HospitalizedPetResponse response = hospitalizedPetService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(response).build());
    }

    @GetMapping("/staff/all")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<APIResponse> getAllForStaff(@RequestParam(defaultValue = "0") int page) {
        Page<HospitalizedPetResponse> responses = hospitalizedPetService.getAllForStaff(page);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responses).build());
    }

    @PutMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<APIResponse> deleteHospitalizedPet(@PathVariable Integer id) throws OperationNotPermittedException {
        hospitalizedPetService.deleteHospitalizedPet(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Hospitalized pet deleted successfully").build());
    }

    @GetMapping("/customer/all")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> getAllForCustomer(Authentication authentication) {
        List<HospitalizedPetResponse> responses = hospitalizedPetService.getAllForCustomer(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responses).build());
    }

    @GetMapping("/services/{id}")
    @PreAuthorize("hasAnyRole('STAFF','USER')")
    public ResponseEntity<APIResponse> getAllServiceById(@PathVariable Integer id) {
        List<HospitalizedServiceResponse> responses = hospitalizedPetService.getAllServiceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responses).build());
    }

    @PostMapping("/pay/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> payHospitalizedFee(@PathVariable Integer id, Authentication authentication) throws OperationNotPermittedException, PayPalRESTException, UserMistake {
        PaymentResponse response = hospitalizedPetService.payHospitalizedFee(id, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(response).build());
    }

    @PutMapping("/discharge/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<APIResponse> dischargeHospitalizedPet(@PathVariable Integer id, Authentication authentication) throws OperationNotPermittedException {
        hospitalizedPetService.dischargeHospitalizedPet(id, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Pet discharged successfully").build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<APIResponse> updateHospitalizedPetForDoctor(@PathVariable Integer id, @RequestBody @Valid UpdatePetRecordDTO dto, Authentication authentication) throws OperationNotPermittedException {
        hospitalizedPetService.updateHospitalizedPetForDoctor(id, dto, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Pet record updated successfully").build());
    }
}
