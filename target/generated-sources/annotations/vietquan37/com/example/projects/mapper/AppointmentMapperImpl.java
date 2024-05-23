package vietquan37.com.example.projects.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Appointment;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;

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
        appointment.paidStatus( false );

        return appointment.build();
    }
}
