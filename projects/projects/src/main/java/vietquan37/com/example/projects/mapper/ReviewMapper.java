package vietquan37.com.example.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vietquan37.com.example.projects.entity.Review;
import vietquan37.com.example.projects.payload.request.ReviewDTO;
import vietquan37.com.example.projects.payload.response.ReviewResponse;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "comment" ,source="comment")
    @Mapping(target = "rating" ,source="rating")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Review mapReviewDTO(ReviewDTO reviewDTO);
    @Mapping(target = "id" ,source="id")
    @Mapping(target = "author" ,source="customer.user.email")
    @Mapping(target = "comment" ,source="comment")
    @Mapping(target = "rating" ,source="rating")
    @Mapping(target = "createdAt", source = "updatedAt")
    ReviewResponse mapReviewResponse(Review review);
    @Mapping(target = "id" ,source="id")
    @Mapping(target = "author" ,source="customer.user.email")
    @Mapping(target = "comment" ,source="comment")
    @Mapping(target = "rating" ,source="rating")
    @Mapping(target = "createdAt", source = "updatedAt")
    @Mapping(target = "appointmentDate" ,source="appointment.appointmentDate")
    ReviewResponse mapCustomerReviewResponse(Review review);

    @Mapping(target = "comment" ,source="comment")
    @Mapping(target = "rating" ,source="rating")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateReviewFromDto(ReviewDTO dto, @MappingTarget Review review);
}
