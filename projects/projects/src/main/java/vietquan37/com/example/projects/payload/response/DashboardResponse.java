package vietquan37.com.example.projects.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private long totalCustomer;
    private long totalStaff;
    private long totalPet;
    private long totalDoctor;
}
