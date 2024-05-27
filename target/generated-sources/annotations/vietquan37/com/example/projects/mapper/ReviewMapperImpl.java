package vietquan37.com.example.projects.mapper;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Appointment;
import vietquan37.com.example.projects.entity.Customer;
import vietquan37.com.example.projects.entity.Review;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.payload.request.ReviewDTO;
import vietquan37.com.example.projects.payload.response.ReviewResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review mapReviewDTO(ReviewDTO reviewDTO) {
        if ( reviewDTO == null ) {
            return null;
        }

        Review.ReviewBuilder review = Review.builder();

        review.comment( reviewDTO.getComment() );
        review.rating( reviewDTO.getRating() );

        review.updatedAt( java.time.LocalDateTime.now() );
        review.createdAt( java.time.LocalDateTime.now() );

        return review.build();
    }

    @Override
    public ReviewResponse mapReviewResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse.ReviewResponseBuilder reviewResponse = ReviewResponse.builder();

        reviewResponse.id( review.getId() );
        reviewResponse.author( reviewCustomerUserEmail( review ) );
        reviewResponse.comment( review.getComment() );
        reviewResponse.rating( review.getRating() );
        reviewResponse.createdAt( review.getUpdatedAt() );

        return reviewResponse.build();
    }

    @Override
    public ReviewResponse mapCustomerReviewResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse.ReviewResponseBuilder reviewResponse = ReviewResponse.builder();

        reviewResponse.id( review.getId() );
        reviewResponse.author( reviewCustomerUserEmail( review ) );
        reviewResponse.comment( review.getComment() );
        reviewResponse.rating( review.getRating() );
        reviewResponse.createdAt( review.getUpdatedAt() );
        reviewResponse.appointmentDate( reviewAppointmentAppointmentDate( review ) );

        return reviewResponse.build();
    }

    @Override
    public void updateReviewFromDto(ReviewDTO dto, Review review) {
        if ( dto == null ) {
            return;
        }

        review.setComment( dto.getComment() );
        review.setRating( dto.getRating() );

        review.setUpdatedAt( java.time.LocalDateTime.now() );
    }

    private String reviewCustomerUserEmail(Review review) {
        if ( review == null ) {
            return null;
        }
        Customer customer = review.getCustomer();
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private LocalDate reviewAppointmentAppointmentDate(Review review) {
        if ( review == null ) {
            return null;
        }
        Appointment appointment = review.getAppointment();
        if ( appointment == null ) {
            return null;
        }
        LocalDate appointmentDate = appointment.getAppointmentDate();
        if ( appointmentDate == null ) {
            return null;
        }
        return appointmentDate;
    }
}
