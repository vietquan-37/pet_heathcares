package vietquan37.com.example.projects.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.HospitalizedPet;
import vietquan37.com.example.projects.enumClass.PetStatus;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class HospitalizedPetMapperImpl implements HospitalizedPetMapper {

    @Override
    public HospitalizedPet mapDto(HospitalizedPetDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HospitalizedPet.HospitalizedPetBuilder hospitalizedPet = HospitalizedPet.builder();

        hospitalizedPet.dailyNote( dto.getDailyNote() );
        hospitalizedPet.diagnosis( dto.getDiagnosis() );
        hospitalizedPet.treatment( dto.getTreatment() );

        hospitalizedPet.admissionDate( java.time.LocalDate.now() );
        hospitalizedPet.status( PetStatus.UNDER_TREATMENT );

        return hospitalizedPet.build();
    }
}
