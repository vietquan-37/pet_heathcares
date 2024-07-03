package vietquan37.com.example.projects.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vietquan37.com.example.projects.enumClass.ServiceTypes;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.ServiceDTO;

import vietquan37.com.example.projects.payload.request.ServiceUpdateDTO;
import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.service.IService;

@RestController
@RequestMapping("api/v1/service")
@RequiredArgsConstructor
public class ServiceController {
    private final IService iService;
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<APIResponse> GetAllService() {
        var response = iService.getAllServicesForAdmin();
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());


    }
    @GetMapping
    public ResponseEntity<APIResponse> GetAllServiceForUser() {
        var response = iService.getAllServicesForAdmin();
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());


    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<APIResponse> GetAllById(@PathVariable Integer id) {
        var response = iService.getServiceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> CreateService(@RequestBody @Valid ServiceDTO dto) throws UserMistake {
      iService.addService(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data("Service created successfully.")
                .build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> UpdateService(@RequestBody @Valid ServiceUpdateDTO dto, @PathVariable Integer id) throws  UserMistake {
        iService.updateService(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Service updated successfully.")
                .build());
    }

    @PatchMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> DeleteUService(@PathVariable Integer id) {
        iService.deleteService(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Service deleted successfully.")
                .build());


    }
    @GetMapping("/types")
    public ResponseEntity<APIResponse> GetServiceByTypes(@RequestParam ServiceTypes type) {
        var response = iService.getAllServiceByType(type);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());


    }


}
