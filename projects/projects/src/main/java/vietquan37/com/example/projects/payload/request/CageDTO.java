package vietquan37.com.example.projects.payload.request;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import vietquan37.com.example.projects.enumClass.CageStatus;
@Data
@NoArgsConstructor
public class CageDTO {
    @NotNull
    private int cageNumber;
    @NotNull
    private CageStatus cageStatus;
    @NotNull
    @Min(value = 2)
    @Max(value = 5)
    private int capacity;
}