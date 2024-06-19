package vietquan37.com.example.projects.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private long totalCustomer;
    private long totalStaff;
    private long totalPet;
    private long totalDoctor;
}
