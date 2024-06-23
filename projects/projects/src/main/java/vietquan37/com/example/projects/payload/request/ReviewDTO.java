package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {
    @NotBlank
    private String comment;
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private int rating;

}
