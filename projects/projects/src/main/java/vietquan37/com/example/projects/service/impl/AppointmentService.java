package vietquan37.com.example.projects.service.impl;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vietquan37.com.example.projects.config.PayPalService;
import vietquan37.com.example.projects.entity.*;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.enumClass.ServiceTypes;
import vietquan37.com.example.projects.enumClass.WorkingDay;
import vietquan37.com.example.projects.exception.DoctorNotAvailableException;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.AppointmentMapper;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;
import vietquan37.com.example.projects.payload.response.AppointmentDataResponse;
import vietquan37.com.example.projects.payload.response.PaymentResponse;
import vietquan37.com.example.projects.repository.*;
import vietquan37.com.example.projects.service.IAppointmentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private static final int MAX = 5;
    private final PaymentRepository paymentRepository;

    @Override
    public Page<AppointmentDataResponse> GetAllUserAppointment(Authentication connectedUser, int page) {
        User user = ((User) connectedUser.getPrincipal());
        var customer = customerRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<Appointment> appointments = appointmentRepository.findAllByCustomerIdAndDeletedIsFalse(customer.getId(), pageable);
        return appointments.map(mapper::mapAppointmentResponseForUser);
    }

    @Override
    public void UpdateAppointment(Integer appointmentId, AppointmentDTO dto, Authentication connectedUser) throws OperationNotPermittedException, DoctorNotAvailableException, UserMistake {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        User user = ((User) connectedUser.getPrincipal());
        if (dto.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new UserMistake("Appointment date cannot be in the past");
        }
        if (!Objects.equals(appointment.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to update this appointment");
        }

        if (!appointment.getAppointmentStatus().equals(AppointmentStatus.PENDING)) {
            throw new UserMistake("Cannot update a paid appointment");
        }
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        var service = serviceRepository.findByIdAndDeletedIsFalseAndType(dto.getServiceId(), ServiceTypes.APPOINTMENT).orElseThrow(() -> new EntityNotFoundException("Service not found"));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        if (!appointment.getAppointmentDate().equals(dto.getAppointmentDate()) && appointmentRepository.findByPetIdAndAppointmentDate(dto.getPetId(), dto.getAppointmentDate()) != null) {
            throw new UserMistake("Appointment for that pet already exists");
        }
        if (isDoctorAvailable(doctor, dto.getAppointmentDate())) {
            throw new DoctorNotAvailableException("Doctor is not available at the specified time");
        }
        if(appointmentRepository.countAppointmentByDoctorIdAndTimeFrameAndAppointmentDateAndAppointmentStatus(doctor.getId(),dto.getTimeFrame(),dto.getAppointmentDate(),AppointmentStatus.BOOKED)>=3){
            throw new DoctorNotAvailableException("Doctor is not available at the specified time");
        }

        appointment.setTimeFrame(dto.getTimeFrame());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentPrice(service.getPrice());
        appointment.setService(service);
        appointment.setDoctor(doctor);
        appointment.setUpdatedAt(LocalDateTime.now());
        appointment.setPet(pet);
        appointmentRepository.save(appointment);
    }

    @Override
    public Page<AppointmentDataResponse> GetDoctorAppointment(Authentication connectedUser, int page) {
        User user = ((User) connectedUser.getPrincipal());
        var doctor = doctorRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<Appointment> appointments = appointmentRepository.findAllByDoctorIdAndDeletedIsFalse(doctor.getId(), pageable);
        return appointments.map(mapper::mapAppointmentResponseForUser);
    }

    @Override
    public AppointmentDataResponse GetAppointmentById(Integer appointmentId, Authentication connectedUser) throws OperationNotPermittedException {
        var appointment = appointmentRepository.findByIdAndDeletedIsFalse(appointmentId).orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(appointment.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to view that pet");
        }
        return mapper.mapAppointmentResponseForUser(appointment);
    }

    @Override
    @Transactional
    public PaymentResponse CreateAppointment(AppointmentDTO dto, Authentication connectedUser)
            throws OperationNotPermittedException, DoctorNotAvailableException, UserMistake, PayPalRESTException {
        if (dto.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new UserMistake("Appointment date cannot be in the past");
        }


        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        var service = serviceRepository.findByIdAndDeletedIsFalseAndType(dto.getServiceId(), ServiceTypes.APPOINTMENT).orElseThrow(() -> new EntityNotFoundException("Service not found"));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        User user = ((User) connectedUser.getPrincipal());
        var customer = customerRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if (appointmentRepository.findByPetIdAndAppointmentDate(dto.getPetId(), dto.getAppointmentDate()) != null) {
            throw new UserMistake("Appointment for that pet already exists");
        }
        if (!Objects.equals(pet.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to create  appointment for that pet");
        }

        if (isDoctorAvailable(doctor, dto.getAppointmentDate())) {
            throw new DoctorNotAvailableException("Doctor is not available at the specified time");
        }
        if(appointmentRepository.countAppointmentByDoctorIdAndTimeFrameAndAppointmentDateAndAppointmentStatus(doctor.getId(),dto.getTimeFrame(),dto.getAppointmentDate(),AppointmentStatus.BOOKED)>=3){
            throw new DoctorNotAvailableException("Doctor is not available at the specified time");
        }


        Appointment appointment = mapper.mapDTO(dto);
        appointment.setTimeFrame(dto.getTimeFrame());
        appointment.setCustomer(customer);
        appointment.setDoctor(doctor);
        appointment.setService(service);
        appointment.setPet(pet);
        BigDecimal adjustedPrice = service.getPrice().subtract(customer.getCustomer_balance());
        if (adjustedPrice.compareTo(BigDecimal.ZERO) < 0) {
            adjustedPrice = BigDecimal.ZERO;
            appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
        }
        appointment.setRefund_payments(BigDecimal.ZERO);
        appointment.setAppointmentPrice(adjustedPrice);
        appointmentRepository.save(appointment);
        if (appointment.getAppointmentPrice().equals(BigDecimal.ZERO)) {

            customer.setCustomer_balance(customer.getCustomer_balance().subtract(service.getPrice()));
            customerRepository.save(customer);
            return PaymentResponse.builder().msg("Appointment booked successfully").build();
        } else {

            return handlePayment(appointment);
        }
    }

    @Transactional
    @Override
    public PaymentResponse RePayAppointment(Integer appointmentId, Authentication connectedUser)
            throws OperationNotPermittedException, PayPalRESTException, UserMistake, DoctorNotAvailableException {


        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        User user = (User) connectedUser.getPrincipal();

        if (!Objects.equals(appointment.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to re-pay for this appointment");
        }


        if (appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new UserMistake("Cannot repay a past appointment");
        }


        if (appointment.getAppointmentStatus().equals(AppointmentStatus.BOOKED)) {
            throw new UserMistake("Appointment is already paid");
        }


        if (appointmentRepository.countAppointmentByDoctorIdAndTimeFrameAndAppointmentDateAndAppointmentStatus(
                appointment.getDoctor().getId(), appointment.getTimeFrame(),
                appointment.getAppointmentDate(), AppointmentStatus.BOOKED) >= 3) {
            throw new DoctorNotAvailableException("Please update the appointment date and timeframe");
        }


        Customer customer = customerRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));


        BigDecimal adjustedPrice = appointment.getService().getPrice().subtract(customer.getCustomer_balance());
        if (adjustedPrice.compareTo(BigDecimal.ZERO) < 0) {
            adjustedPrice = BigDecimal.ZERO;
            appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
        }
        appointment.setAppointmentPrice(adjustedPrice);
        appointmentRepository.save(appointment);


        if (appointment.getAppointmentPrice().compareTo(BigDecimal.ZERO) == 0) {
            customer.setCustomer_balance(customer.getCustomer_balance().subtract(appointment.getService().getPrice()));
            customerRepository.save(customer);
            appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
            appointmentRepository.save(appointment);
            return PaymentResponse.builder().msg("Appointment booked successfully").build();
        } else {
            return handlePayment(appointment);
        }
    }
    private PaymentResponse handlePayment(Appointment appointment) throws PayPalRESTException {
        try {
            Payment payment = payPalService.createPayment(
                    appointment.getAppointmentPrice().toString(),
                    "Appointment payment"
            );

            if (payment == null || payment.getId() == null) {
                throw new PayPalRESTException("Payment creation failed");
            }

            if (appointment.getPayments() != null) {
                Payments previousPayments = appointment.getPayments();
                appointment.setPayments(null);
                appointmentRepository.save(appointment);
                paymentRepository.delete(previousPayments);
            }

            Payments payments = new Payments();
            payments.setPaymentId(payment.getId());
            paymentRepository.save(payments);
            appointment.setPayments(payments);
            appointmentRepository.save(appointment);

            for (Links link : payment.getLinks()) {
                if ("approval_url".equals(link.getRel())) {
                    return PaymentResponse.builder().paymentUrl(link.getHref()).build();
                }
            }
        } catch (PayPalRESTException e) {
            throw new PayPalRESTException("Payment creation failed", e);
        }

        throw new PayPalRESTException("Payment creation failed");
    }


    @Override
    public Page<AppointmentDataResponse> GetAllAppointment(int page) {

        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<Appointment> appointments = appointmentRepository.findAllByDeletedIsFalse(pageable);
        return appointments.map(mapper::mapAppointmentResponseForUser);
    }

    @Override
    public void CancelAppointment(Integer appointmentId, Authentication connectedUser) throws OperationNotPermittedException, UserMistake {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        User user = ((User) connectedUser.getPrincipal());
        var customer = customerRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if (!Objects.equals(appointment.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to cancel that appointment");
        }

        if (!appointment.getAppointmentStatus().equals(AppointmentStatus.BOOKED)) {
            throw new UserMistake("Cannot cancel this appointment");
        }

        if (appointment.getAppointmentDate().isBefore(LocalDate.now()) || appointment.getAppointmentDate().isEqual(LocalDate.now())) {
            throw new UserMistake("Cannot cancel the passed or today's appointment");
        }

        long daysUntilAppointment = ChronoUnit.DAYS.between(LocalDate.now(), appointment.getAppointmentDate());
        if (daysUntilAppointment >= 7) {
            appointment.setRefund_payments(appointment.getService().getPrice());
            customer.setCustomer_balance(customer.getCustomer_balance().add(appointment.getRefund_payments()));
        } else if (daysUntilAppointment >= 3) {
            appointment.setRefund_payments(appointment.getService().getPrice().multiply(BigDecimal.valueOf(0.75)));
            customer.setCustomer_balance(customer.getCustomer_balance().add(appointment.getRefund_payments()));
        } else {
            appointment.setRefund_payments(BigDecimal.ZERO);
            customer.setCustomer_balance(customer.getCustomer_balance().add(appointment.getRefund_payments()));

        }

        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        customerRepository.save(customer);
        appointmentRepository.save(appointment);
    }


    private boolean isDoctorAvailable(Doctor doctor, LocalDate appointmentDate) {
        List<WorkingDay> workingDays = doctor.getWorkingDay();
        WorkingDay appointmentDay = WorkingDay.valueOf(appointmentDate.getDayOfWeek().name());
        return !workingDays.contains(appointmentDay);
    }

    @Override
    public void DeleteAppointment(Integer appointmentId, Authentication connectedUser) throws OperationNotPermittedException, UserMistake {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        User user = ((User) connectedUser.getPrincipal());
        if (!appointment.getAppointmentStatus().equals(AppointmentStatus.PENDING)) {
            throw new UserMistake("Cannot delete this appointment");
        }
        if (!Objects.equals(appointment.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to cancel that appointment");
        }
        appointment.setDeleted(true);
        appointmentRepository.save(appointment);
    }

}

