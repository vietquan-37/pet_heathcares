package vietquan37.com.example.projects.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vietquan37.com.example.projects.entity.Appointment;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;
import vietquan37.com.example.projects.payload.response.AppointmentDataResponse;


@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "appointmentStatus", constant = "PENDING")
    @Mapping(target = "appointmentDate", source = "appointmentDate")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deleted", constant = "false")
    Appointment mapDTO(AppointmentDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "appointmentDate", source = "appointmentDate")
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "doctorName", source = "doctor.user.fullName")
    @Mapping(target = "appointmentPrice", source = "appointmentPrice")
    @Mapping(target = "refund_payments", source = "refund_payments")
    @Mapping(target = "service", source = "service.name")
    @Mapping(target = "appointmentStatus", source = "appointmentStatus")
    AppointmentDataResponse mapAppointmentResponseForUser(Appointment appointment);

}
