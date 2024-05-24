package vietquan37.com.example.projects.config;


import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.entity.Appointment;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.repository.AppointmentRepository;
import vietquan37.com.example.projects.repository.CustomerRepository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayPalService {
    @Value("${paypal.cancelUrl}")
    private String cancelUrl;
    @Value("${paypal.successUrl}")
    private String successUrl;
    private final APIContext apiContext;
    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;


    public Payment createPayment(Appointment appointment) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(appointment.getAppointmentPrice().toString());

        Transaction transaction = new Transaction();
        transaction.setDescription("Appointment payment");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }


    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }


    public void updateAppointmentPaymentStatus(String paymentId) throws PayPalRESTException {
        Appointment appointment = appointmentRepository.findByPaymentId(paymentId).orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        appointment.setPaidStatus(true);
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
        var customer=customerRepository.findByUser_Id(appointment.getCustomer().getUser().getId()).get();
        customer.setCustomer_balance(BigDecimal.ZERO);
        customerRepository.save(customer);
        appointmentRepository.save(appointment);
    }
}
