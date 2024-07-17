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
import vietquan37.com.example.projects.payload.request.DailyNoteDTO;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetRecordDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetServiceDTO;
import vietquan37.com.example.projects.payload.response.*;
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
    public ResponseEntity<APIResponse> updateServiceForPet(@PathVariable Integer id, @RequestBody @Valid UpdatePetServiceDTO dto) throws  UserMistake {
        hospitalizedPetService.updateServiceForPet(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Service updated successfully").build());
    }
    @PostMapping("/note/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<APIResponse> addDailyNote(@PathVariable Integer id, @RequestBody @Valid DailyNoteDTO dto,Authentication authentication) throws UserMistake, OperationNotPermittedException {
        hospitalizedPetService.addDailyNote(id,dto,authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data("Daily note added successfully").build());
    }
    @PatchMapping("/note/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<APIResponse> updateDailyNote(@PathVariable Integer id, @RequestBody @Valid DailyNoteDTO dto,Authentication authentication) throws UserMistake, OperationNotPermittedException {
        hospitalizedPetService.updateDailyNote(id,dto,authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Daily note updated successfully").build());
    }
    @GetMapping("/notes/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','USER')")
    public ResponseEntity<APIResponse> getAllDailyNoteById(@PathVariable Integer id,Authentication authentication,@RequestParam(defaultValue = "0") int page) throws OperationNotPermittedException {
        Page<DailyNoteResponse> responses = hospitalizedPetService.getAllDailyNotes(id,authentication,page);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responses).build());
    }
    @GetMapping("/note/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','USER')")
    public ResponseEntity<APIResponse> getDailyNoteById(@PathVariable Integer id,Authentication authentication) throws OperationNotPermittedException {
      var responses = hospitalizedPetService.getDailyNoteById(id,authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responses).build());
    }


    @DeleteMapping("/deleteService/{id}")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<APIResponse> deleteServiceForPet(@PathVariable Integer id) throws UserMistake {
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
    public ResponseEntity<APIResponse> deleteHospitalizedPet(@PathVariable Integer id) throws OperationNotPermittedException, UserMistake {
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
    @GetMapping("/record/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> getAllPetRecordById(Authentication authentication,Integer id) throws OperationNotPermittedException {
        List<HospitalizedPetResponse> responses = hospitalizedPetService.getAllHospitalizedPetByPetId(authentication,id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data(responses).build());
    }

    @GetMapping("/services/{id}")
    @PreAuthorize("hasAnyRole('STAFF','USER')")
    public ResponseEntity<APIResponse> getAllServiceById(@PathVariable Integer id,Authentication authentication,@RequestParam(defaultValue = "0") int page) throws OperationNotPermittedException {
        Page<HospitalizedServiceResponse> responses = hospitalizedPetService.getAllServiceById(id,authentication,page);
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
    public ResponseEntity<APIResponse> dischargeHospitalizedPet(@PathVariable Integer id, Authentication authentication) throws OperationNotPermittedException, UserMistake {
        hospitalizedPetService.dischargeHospitalizedPet(id, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Pet discharged successfully").build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<APIResponse> updateHospitalizedPetForDoctor(@PathVariable Integer id, @RequestBody @Valid UpdatePetRecordDTO dto, Authentication authentication) throws OperationNotPermittedException, UserMistake {
        hospitalizedPetService.updateHospitalizedPetForDoctor(id, dto, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Pet record updated successfully").build());
    }
}
