package vietquan37.com.example.projects.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {
    private Integer id;
    private String author;
    private String comment;
    private int rating;
    private LocalDate appointmentDate;
    private LocalDateTime createdAt;
}
