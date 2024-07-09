package vietquan37.com.example.projects.service;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.ReviewDTO;
import vietquan37.com.example.projects.payload.response.ReviewResponse;

import java.util.List;

public interface IReviewService {
    void addReview(Integer appointmentId, ReviewDTO review, Authentication authentication) throws UserMistake, OperationNotPermittedException;

    List<ReviewResponse> getAllReviews();

    void updateReview(Integer id, ReviewDTO dto, Authentication authentication) throws OperationNotPermittedException;

    List<ReviewResponse> getAllReviewByUser(Authentication authentication);
    ReviewResponse getReviewById(Integer id);
}
