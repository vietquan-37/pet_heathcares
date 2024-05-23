package vietquan37.com.example.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vietquan37.com.example.projects.entity.Appointment;
import vietquan37.com.example.projects.payload.request.AppointmentDTO;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(target = "appointmentStatus", constant = "PENDING")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "paidStatus", constant = "false")
    Appointment mapDTO(AppointmentDTO dto);
}
