package vietquan37.com.example.projects.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vietquan37.com.example.projects.entity.HospitalizedPet;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;

@Mapper(componentModel = "spring")
public interface HospitalizedPetMapper {
    @Mapping(target = "dailyNote" ,source = "dailyNote")
    @Mapping(target = "diagnosis" ,source = "diagnosis")
    @Mapping(target = "treatment" ,source = "treatment")
    @Mapping(target = "admissionDate" ,expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "status" ,constant = "UNDER_TREATMENT")
    HospitalizedPet mapDto(HospitalizedPetDTO dto);

}



