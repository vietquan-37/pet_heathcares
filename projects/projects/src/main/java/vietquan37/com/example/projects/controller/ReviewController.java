package vietquan37.com.example.projects.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.ReviewDTO;

import vietquan37.com.example.projects.payload.response.APIResponse;
import vietquan37.com.example.projects.service.IReviewService;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping
    public ResponseEntity<APIResponse> getReview(@RequestParam(defaultValue = "0") int page) {
        var response = reviewService.getAllReviews(page);
        return ResponseEntity
                .ok(APIResponse.builder().data(response).status(HttpStatus.OK.value()).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> GetAllById(@PathVariable Integer id) {
        var response = reviewService.getReviewById(id);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<APIResponse> GetAllUserReviewById(Authentication authentication) {
        var response = reviewService.getAllReviewByUser(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().status(HttpStatus.OK.value()).data(response).build());
    }

    @PostMapping("/create/{appointmentId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> CreateUser(@PathVariable Integer appointmentId, @RequestBody @Valid ReviewDTO dto, Authentication authentication) throws UserMistake, OperationNotPermittedException {
        reviewService.addReview(appointmentId, dto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .data("User registered successfully.")
                .build());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<APIResponse> UpdateUser(@PathVariable Integer id, @RequestBody @Valid ReviewDTO dto, Authentication authentication) throws OperationNotPermittedException {
        reviewService.updateReview(id, dto, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder()
                .status(HttpStatus.OK.value())
                .data("User updated successfully.")
                .build());
    }

}
