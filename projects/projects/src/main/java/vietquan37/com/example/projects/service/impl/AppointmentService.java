package vietquan37.com.example.projects.service.impl;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.config.PayPalService;
import vietquan37.com.example.projects.entity.*;
import vietquan37.com.example.projects.enumClass.WorkingDay;
import vietquan37.com.example.projects.exception.DoctorNotAvailableException;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.AppointmentMapper;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;
import vietquan37.com.example.projects.payload.response.AppointmentResponse;
import vietquan37.com.example.projects.repository.*;
import vietquan37.com.example.projects.service.IAppointmentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final DoctorRepository doctorRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper mapper;
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;
    private final PayPalService payPalService;

    @Override
    public AppointmentResponse CreateAppointment(AppointmentDTO dto, Authentication connectedUser)
            throws OperationNotPermittedException, DoctorNotAvailableException, UserMistake, PayPalRESTException {
        if (dto.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new UserMistake("Appointment date cannot be in the past");
        }

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        var service = serviceRepository.findById(dto.getServiceId()).orElseThrow(() -> new EntityNotFoundException("Service not found"));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        User user = ((User) connectedUser.getPrincipal());
        var customer = customerRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if (!Objects.equals(pet.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to update that pet");
        }

        if (!isDoctorAvailable(doctor, dto.getAppointmentDate())) {
            throw new DoctorNotAvailableException("Doctor is not available at the specified time");
        }

        Appointment appointment = mapper.mapDTO(dto);
        appointment.setCustomer(customer);
        appointment.setAppointmentPrice(service.getPrice());
        appointment.setDoctor(doctor);
        appointment.setService(service);
        appointment.setPet(pet);
        appointment.setRefund_payments(BigDecimal.ZERO);
        appointment.setCustomer(customerRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found")));
        appointmentRepository.save(appointment);
        try {
            Payment payment = payPalService.createPayment(appointment);
            appointment.setPaymentId(payment.getId());
            appointmentRepository.save(appointment);
            for (Links link : payment.getLinks()) {
                if ("approval_url".equals(link.getRel())) {
                  var payUrl = link.getHref();
                    return AppointmentResponse.builder().paymentUrl(payUrl).build();
                }

            }

        } catch (PayPalRESTException e) {
            throw new PayPalRESTException("Payment creation failed");
        }
        throw new PayPalRESTException("Payment creation failed");
    }

    private boolean isDoctorAvailable(Doctor doctor, LocalDate appointmentDate) {
        List<WorkingDay> workingDays = doctor.getWorkingDay();
        WorkingDay appointmentDay = WorkingDay.valueOf(appointmentDate.getDayOfWeek().name());

        return workingDays.contains(appointmentDay);
    }

}

