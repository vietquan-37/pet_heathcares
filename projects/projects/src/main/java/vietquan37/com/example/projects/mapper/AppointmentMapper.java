package vietquan37.com.example.projects.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import vietquan37.com.example.projects.entity.Appointment;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;
import vietquan37.com.example.projects.payload.response.AppointmentDataResponse;
import vietquan37.com.example.projects.payload.response.AppointmentResponse;


@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "appointmentStatus", constant = "PENDING")
    @Mapping(target = "appointmentDate", source = "appointmentDate")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "paidStatus", constant = "false")
    Appointment mapDTO(AppointmentDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "appointmentDate", source = "appointmentDate")
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "doctorName", source = "doctor.user.fullName")
    @Mapping(target = "appointmentPrice", source = "appointmentPrice")
    @Mapping(target = "refund_payments", source = "refund_payments")
    @Mapping(target = "paidStatus", source = "paidStatus")
    @Mapping(target = "service", source = "service.type")
    @Mapping(target = "appointmentStatus", source = "appointmentStatus")
    AppointmentDataResponse mapAppointmentResponseForUser(Appointment appointment);

}
