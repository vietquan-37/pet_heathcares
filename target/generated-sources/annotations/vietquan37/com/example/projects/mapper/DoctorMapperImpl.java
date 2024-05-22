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
    public Doctor doctorDTOToDoctor(DoctorDTO doctorDTO, Doctor doctor) {
        if ( doctorDTO == null ) {
            return doctor;
        }

        doctor.setSpecialty( doctorDTO.getSpecialty() );
        doctor.setStart_time( doctorDTO.getStart_time() );
        doctor.setEnd_time( doctorDTO.getEnd_time() );
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

        return doctor;
    }

    @Override
    public DoctorResponse mapDoctorResponse(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorResponse.DoctorResponseBuilder doctorResponse = DoctorResponse.builder();

        doctorResponse.fullName( doctorUserFullName( doctor ) );
        doctorResponse.email( doctorUserEmail( doctor ) );
        doctorResponse.imageUrl( doctor.getImageUrl() );
        doctorResponse.specialty( doctor.getSpecialty() );

        return doctorResponse.build();
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
