package vietquan37.com.example.projects.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Appointment;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.entity.Pet;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;
import vietquan37.com.example.projects.payload.response.AppointmentDataResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public Appointment mapDTO(AppointmentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Appointment.AppointmentBuilder appointment = Appointment.builder();

        appointment.appointmentDate( dto.getAppointmentDate() );

        appointment.appointmentStatus( AppointmentStatus.PENDING );
        appointment.updatedAt( java.time.LocalDateTime.now() );
        appointment.createdAt( java.time.LocalDateTime.now() );
        appointment.deleted( false );

        return appointment.build();
    }

    @Override
    public AppointmentDataResponse mapAppointmentResponseForUser(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentDataResponse.AppointmentDataResponseBuilder appointmentDataResponse = AppointmentDataResponse.builder();

        appointmentDataResponse.id( appointment.getId() );
        appointmentDataResponse.appointmentDate( appointment.getAppointmentDate() );
        appointmentDataResponse.petName( appointmentPetName( appointment ) );
        appointmentDataResponse.doctorName( appointmentDoctorUserFullName( appointment ) );
        appointmentDataResponse.appointmentPrice( appointment.getAppointmentPrice() );
        appointmentDataResponse.refund_payments( appointment.getRefund_payments() );
        appointmentDataResponse.service( appointmentServiceName( appointment ) );
        appointmentDataResponse.appointmentStatus( appointment.getAppointmentStatus() );
        appointmentDataResponse.deleted( appointment.isDeleted() );
        appointmentDataResponse.createdAt( appointment.getCreatedAt() );
        appointmentDataResponse.updatedAt( appointment.getUpdatedAt() );

        return appointmentDataResponse.build();
    }

    private String appointmentPetName(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Pet pet = appointment.getPet();
        if ( pet == null ) {
            return null;
        }
        String name = pet.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String appointmentDoctorUserFullName(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Doctor doctor = appointment.getDoctor();
        if ( doctor == null ) {
            return null;
        }
        User user = doctor.getUser();
        if ( user == null ) {
            return null;
        }
        String fullName = user.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }

    private String appointmentServiceName(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Services service = appointment.getService();
        if ( service == null ) {
            return null;
        }
        String name = service.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
