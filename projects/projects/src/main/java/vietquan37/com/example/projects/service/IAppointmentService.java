package vietquan37.com.example.projects.service;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.security.core.Authentication;
import vietquan37.com.example.projects.exception.DoctorNotAvailableException;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;
import vietquan37.com.example.projects.payload.response.AppointmentResponse;

public interface IAppointmentService {
    AppointmentResponse CreateAppointment(AppointmentDTO dto, Authentication connectedUser) throws OperationNotPermittedException, DoctorNotAvailableException, UserMistake, PayPalRESTException;
}