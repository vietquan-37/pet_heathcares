package vietquan37.com.example.projects.service;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import vietquan37.com.example.projects.exception.DoctorNotAvailableException;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;
import vietquan37.com.example.projects.payload.response.AppointmentDataResponse;
import vietquan37.com.example.projects.payload.response.AppointmentResponse;

public interface IAppointmentService {
    AppointmentResponse CreateAppointment(AppointmentDTO dto, Authentication connectedUser) throws OperationNotPermittedException, DoctorNotAvailableException, UserMistake, PayPalRESTException;

    void CancelAppointment(Integer appointmentId, Authentication connectedUser) throws OperationNotPermittedException, UserMistake;

    void DeleteAppointment(Integer appointmentId, Authentication connectedUser) throws OperationNotPermittedException, UserMistake;

    AppointmentDataResponse GetAppointmentById(Integer appointmentId, Authentication connectedUser) throws OperationNotPermittedException;

    Page<AppointmentDataResponse> GetAllUserAppointment(Authentication connectedUser, int page);
    Page<AppointmentDataResponse> GetAllAppointment( int page);
    void UpdateAppointment(Integer appointmentId, AppointmentDTO dto, Authentication connectedUser) throws OperationNotPermittedException, DoctorNotAvailableException, UserMistake;

    AppointmentResponse RePayAppointment(Integer appointmentId, Authentication connectedUser)
            throws OperationNotPermittedException, PayPalRESTException, UserMistake;
}
