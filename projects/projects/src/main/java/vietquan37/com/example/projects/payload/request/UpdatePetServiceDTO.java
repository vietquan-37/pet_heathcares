package vietquan37.com.example.projects.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePetServiceDTO {
    @Size(min = 1)
    private List<Integer> serviceIds;
}
