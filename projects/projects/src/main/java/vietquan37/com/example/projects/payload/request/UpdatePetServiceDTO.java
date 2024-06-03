package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdatePetServiceDTO {
    @Size(min = 1)
    private List<Integer> serviceIds;
}
