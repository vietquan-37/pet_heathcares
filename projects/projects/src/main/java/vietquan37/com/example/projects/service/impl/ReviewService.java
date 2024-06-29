package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.entity.Review;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.ReviewMapper;
import vietquan37.com.example.projects.payload.request.ReviewDTO;
import vietquan37.com.example.projects.payload.response.ReviewResponse;
import vietquan37.com.example.projects.repository.AppointmentRepository;
import vietquan37.com.example.projects.repository.CustomerRepository;
import vietquan37.com.example.projects.repository.ReviewRepository;
import vietquan37.com.example.projects.service.IReviewService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    private final AppointmentRepository appointmentRepository;
    private static final int MAX = 5;
    private final CustomerRepository customerRepository;

    @Override
    public void addReview(Integer appointmentId, ReviewDTO dto, Authentication authentication) throws UserMistake, OperationNotPermittedException {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new EntityNotFoundException("appointment not found"));
        User user = ((User) authentication.getPrincipal());
        var customer = customerRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("customer not found"));
        if (!appointment.getAppointmentStatus().equals(AppointmentStatus.BOOKED) || appointment.getAppointmentDate().isAfter(LocalDate.now())) {
            throw new UserMistake("you can not review this appointment");
        }
        if (!Objects.equals(appointment.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to update this appointment");
        }

        Review review = reviewMapper.mapReviewDTO(dto);
        review.setCustomer(customer);
        reviewRepository.save(review);
        appointment.setReview(review);
        appointmentRepository.save(appointment);
    }

    @Override
    public Page<ReviewResponse> getAllReviews(int page) {
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<Review> reviews = reviewRepository.findAll(pageable);
        return reviews.map(reviewMapper::mapReviewResponse);
    }

    @Override
    public void updateReview(Integer id, ReviewDTO dto, Authentication authentication) throws OperationNotPermittedException {
        var review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("review not found"));
        User user = ((User) authentication.getPrincipal());
        if (!Objects.equals(review.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to update this appointment");
        }
        reviewMapper.updateReviewFromDto(dto, review);
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponse> getAllReviewByUser(Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        var customer = customerRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("customer not found"));

        return reviewRepository.findAllByCustomerId(customer.getId())
                .stream()
                .map(reviewMapper::mapCustomerReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse getReviewById(Integer id) {
        var review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("review not found"));
        return reviewMapper.mapReviewResponse(review);
    }


}
