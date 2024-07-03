package vietquan37.com.example.projects.controller;

import com.paypal.base.rest.PayPalRESTException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vietquan37.com.example.projects.exception.DoctorNotAvailableException;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;
import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.service.IAppointmentService;


@RestController
@RequestMapping("api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final IAppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> GetAllUserAppointment(Authentication authentication, @RequestParam(defaultValue = "0") int page) {
        var response = appointmentService.GetAllUserAppointment(authentication, page);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @GetMapping("/doctor")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<APIResponse> GetAllDoctorAppointment(Authentication authentication, @RequestParam(defaultValue = "0") int page) {
        var response = appointmentService.GetDoctorAppointment(authentication, page);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<APIResponse> GetAllAppointment(@RequestParam(defaultValue = "0") int page) {
        var response = appointmentService.GetAllAppointment(page);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> GetUserAppointmentById(Authentication authentication, @PathVariable Integer appointmentId) throws OperationNotPermittedException {
        var response = appointmentService.GetAppointmentById(appointmentId, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> CreateAppointment(@RequestBody @Valid AppointmentDTO dto, Authentication authentication) throws DoctorNotAvailableException, OperationNotPermittedException, UserMistake, PayPalRESTException {
        var response = appointmentService.CreateAppointment(dto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder().status(HttpStatus.CREATED.value()).data(response).build());
    }

    @PutMapping("/cancel/{appointmentId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> CancelAppointment(@PathVariable Integer appointmentId, Authentication authentication) throws OperationNotPermittedException, UserMistake {
        appointmentService.CancelAppointment(appointmentId, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data("Appointment Cancelled").build());
    }

    @PutMapping("/update/{appointmentId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> UpdateAppointment(@PathVariable Integer appointmentId, @RequestBody AppointmentDTO dto, Authentication authentication) throws DoctorNotAvailableException, OperationNotPermittedException, UserMistake {
        appointmentService.UpdateAppointment(appointmentId, dto, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data("Appointment updated successfully").build());
    }

    @PatchMapping("/delete/{appointmentId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> DeleteAppointment(@PathVariable Integer appointmentId, Authentication authentication) throws DoctorNotAvailableException, OperationNotPermittedException, UserMistake, PayPalRESTException {
        appointmentService.DeleteAppointment(appointmentId, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data("Appointment deleted successfully").build());
    }

    @PutMapping("repay/{appointmentId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> RepayAppointment(@PathVariable Integer appointmentId, Authentication authentication) throws OperationNotPermittedException, PayPalRESTException, UserMistake {
        var url = appointmentService.RePayAppointment(appointmentId, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(url).build());
    }
}
