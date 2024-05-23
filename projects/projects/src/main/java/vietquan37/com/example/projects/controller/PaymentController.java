package vietquan37.com.example.projects.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vietquan37.com.example.projects.config.PayPalService;
import vietquan37.com.example.projects.payload.response.APIResponse;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private final PayPalService service;

    @GetMapping("/cancel")
    public ResponseEntity<APIResponse> cancelPayment() {
        return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data("Payment canceled").build());
    }


    @GetMapping("/success")
    public ResponseEntity<APIResponse> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                service.updateAppointmentPaymentStatus(paymentId);

                return ResponseEntity.ok(APIResponse.builder().status(HttpStatus.OK.value()).data("Payment successful").build());
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).data("Error executing payment.").build());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(APIResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).data("Payment not approved.").build());
    }
}

