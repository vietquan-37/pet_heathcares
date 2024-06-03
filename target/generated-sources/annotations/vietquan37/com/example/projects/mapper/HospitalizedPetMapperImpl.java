package vietquan37.com.example.projects.mapper;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Cage;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.entity.HospitalizedPet;
import vietquan37.com.example.projects.entity.HospitalizedPetServices;
import vietquan37.com.example.projects.entity.Pet;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.PetStatus;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetRecordDTO;
import vietquan37.com.example.projects.payload.response.HospitalizedPetResponse;
import vietquan37.com.example.projects.payload.response.HospitalizedServiceResponse;

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

        hospitalizedPet.admissionDate( java.time.LocalDate.now() );
        hospitalizedPet.status( PetStatus.UNDER_TREATMENT );
        hospitalizedPet.paid( false );

        return hospitalizedPet.build();
    }

    @Override
    public void mapUpdatePetRecord(UpdatePetRecordDTO dto, HospitalizedPet existingPet) {
        if ( dto == null ) {
            return;
        }

        existingPet.setDailyNote( dto.getDailyNote() );
        existingPet.setDiagnosis( dto.getDiagnosis() );
        existingPet.setTreatment( dto.getTreatment() );
    }

    @Override
    public HospitalizedPetResponse mapForDoctor(HospitalizedPet pet) {
        if ( pet == null ) {
            return null;
        }

        HospitalizedPetResponse.HospitalizedPetResponseBuilder hospitalizedPetResponse = HospitalizedPetResponse.builder();

        hospitalizedPetResponse.id( pet.getId() );
        hospitalizedPetResponse.dailyNote( pet.getDailyNote() );
        hospitalizedPetResponse.diagnosis( pet.getDiagnosis() );
        hospitalizedPetResponse.treatment( pet.getTreatment() );
        hospitalizedPetResponse.admissionDate( pet.getAdmissionDate() );
        hospitalizedPetResponse.dischargeDate( pet.getDischargeDate() );
        hospitalizedPetResponse.status( pet.getStatus() );
        hospitalizedPetResponse.cageNumber( petCageCageNumber( pet ) );
        hospitalizedPetResponse.petName( petPetName( pet ) );
        hospitalizedPetResponse.totalPrice( pet.getTotalPrice() );
        hospitalizedPetResponse.paid( pet.isPaid() );

        return hospitalizedPetResponse.build();
    }

    @Override
    public HospitalizedPetResponse mapForResponse(HospitalizedPet pet) {
        if ( pet == null ) {
            return null;
        }

        HospitalizedPetResponse.HospitalizedPetResponseBuilder hospitalizedPetResponse = HospitalizedPetResponse.builder();

        hospitalizedPetResponse.id( pet.getId() );
        hospitalizedPetResponse.dailyNote( pet.getDailyNote() );
        hospitalizedPetResponse.diagnosis( pet.getDiagnosis() );
        hospitalizedPetResponse.treatment( pet.getTreatment() );
        hospitalizedPetResponse.admissionDate( pet.getAdmissionDate() );
        hospitalizedPetResponse.dischargeDate( pet.getDischargeDate() );
        hospitalizedPetResponse.status( pet.getStatus() );
        hospitalizedPetResponse.cageNumber( petCageCageNumber( pet ) );
        hospitalizedPetResponse.doctorName( petDoctorUserFullName( pet ) );
        hospitalizedPetResponse.petName( petPetName( pet ) );
        hospitalizedPetResponse.paid( pet.isPaid() );
        hospitalizedPetResponse.totalPrice( pet.getTotalPrice() );

        return hospitalizedPetResponse.build();
    }

    @Override
    public HospitalizedServiceResponse mapForService(HospitalizedPetServices pet) {
        if ( pet == null ) {
            return null;
        }

        HospitalizedServiceResponse.HospitalizedServiceResponseBuilder hospitalizedServiceResponse = HospitalizedServiceResponse.builder();

        hospitalizedServiceResponse.id( pet.getId() );
        hospitalizedServiceResponse.serviceName( petServiceName( pet ) );
        hospitalizedServiceResponse.price( petServicePrice( pet ) );
        hospitalizedServiceResponse.dateUsage( pet.getUsageDate() );

        return hospitalizedServiceResponse.build();
    }

    private int petCageCageNumber(HospitalizedPet hospitalizedPet) {
        if ( hospitalizedPet == null ) {
            return 0;
        }
        Cage cage = hospitalizedPet.getCage();
        if ( cage == null ) {
            return 0;
        }
        int cageNumber = cage.getCageNumber();
        return cageNumber;
    }

    private String petPetName(HospitalizedPet hospitalizedPet) {
        if ( hospitalizedPet == null ) {
            return null;
        }
        Pet pet = hospitalizedPet.getPet();
        if ( pet == null ) {
            return null;
        }
        String name = pet.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String petDoctorUserFullName(HospitalizedPet hospitalizedPet) {
        if ( hospitalizedPet == null ) {
            return null;
        }
        Doctor doctor = hospitalizedPet.getDoctor();
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

    private String petServiceName(HospitalizedPetServices hospitalizedPetServices) {
        if ( hospitalizedPetServices == null ) {
            return null;
        }
        Services service = hospitalizedPetServices.getService();
        if ( service == null ) {
            return null;
        }
        String name = service.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal petServicePrice(HospitalizedPetServices hospitalizedPetServices) {
        if ( hospitalizedPetServices == null ) {
            return null;
        }
        Services service = hospitalizedPetServices.getService();
        if ( service == null ) {
            return null;
        }
        BigDecimal price = service.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }
}
