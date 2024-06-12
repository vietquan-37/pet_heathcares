package vietquan37.com.example.projects.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.response.DoctorResponse;


@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(target = "specialty", source = "specialty")
    @Mapping(target = "start_time", source = "start_time")
    @Mapping(target = "end_time", source = "end_time")
    @Mapping(target = "workingDay", source = "workingDay")
    @Mapping(target = "user.updatedAt",expression ="java(java.time.LocalDateTime.now())" )
    void doctorDTOToDoctor(DoctorDTO doctorDTO, @MappingTarget Doctor doctor);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "specialty", source = "specialty")
    @Mapping(target = "start_time", source = "start_time")
    @Mapping(target = "end_time", source = "end_time")
    @Mapping(target = "workingDay", source = "workingDay")
    DoctorResponse  mapDoctorResponseForInfo(Doctor doctor);

}
