package vietquan37.com.example.projects.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.WorkingDay;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.response.DoctorResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class DoctorMapperImpl implements DoctorMapper {

    @Override
    public void doctorDTOToDoctor(DoctorDTO doctorDTO, Doctor doctor) {
        if ( doctorDTO == null ) {
            return;
        }

        doctor.setSpecialty( doctorDTO.getSpecialty() );
        if ( doctor.getWorkingDay() != null ) {
            List<WorkingDay> list = doctorDTO.getWorkingDay();
            if ( list != null ) {
                doctor.getWorkingDay().clear();
                doctor.getWorkingDay().addAll( list );
            }
            else {
                doctor.setWorkingDay( null );
            }
        }
        else {
            List<WorkingDay> list = doctorDTO.getWorkingDay();
            if ( list != null ) {
                doctor.setWorkingDay( new ArrayList<WorkingDay>( list ) );
            }
        }
        if ( doctor.getUser() == null ) {
            doctor.setUser( User.builder().build() );
        }
        doctorDTOToUser( doctorDTO, doctor.getUser() );

        doctor.setStartTime( java.time.LocalTime.of(8, 0) );
        doctor.setEndTime( java.time.LocalTime.of(17, 0) );
    }

    @Override
    public DoctorResponse mapDoctorResponseForInfo(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorResponse.DoctorResponseBuilder doctorResponse = DoctorResponse.builder();

        doctorResponse.id( doctor.getId() );
        doctorResponse.fullName( doctorUserFullName( doctor ) );
        doctorResponse.startTime( doctor.getStartTime() );
        doctorResponse.endTime( doctor.getEndTime() );
        doctorResponse.email( doctorUserEmail( doctor ) );
        doctorResponse.imageUrl( doctor.getImageUrl() );
        doctorResponse.specialty( doctor.getSpecialty() );
        List<WorkingDay> list = doctor.getWorkingDay();
        if ( list != null ) {
            doctorResponse.workingDay( new ArrayList<WorkingDay>( list ) );
        }

        return doctorResponse.build();
    }

    protected void doctorDTOToUser(DoctorDTO doctorDTO, User mappingTarget) {
        if ( doctorDTO == null ) {
            return;
        }

        mappingTarget.setUpdatedAt( java.time.LocalDateTime.now() );
    }

    private String doctorUserFullName(Doctor doctor) {
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

    private String doctorUserEmail(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }
        User user = doctor.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
