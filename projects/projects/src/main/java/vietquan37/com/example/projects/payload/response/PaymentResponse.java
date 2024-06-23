package vietquan37.com.example.projects.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PaymentResponse {
    private String paymentUrl;
    private String msg;
}
