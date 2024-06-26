package vietquan37.com.example.projects.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import vietquan37.com.example.projects.enumClass.TimeFrame;
import vietquan37.com.example.projects.exception.FileException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.request.DoctorSearchDTO;
import vietquan37.com.example.projects.payload.response.DoctorResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IDoctorService {
    void UpdateDoctor(Integer id, DoctorDTO dto);
    List<DoctorResponse> GetAllDoctors();
    void UploadImage(Integer id, MultipartFile image) throws FileException, IOException;
    List<DoctorResponse> GetAllDoctorForAdmin();
    DoctorResponse GetDoctorById(Integer id);
    DoctorResponse GetDoctorInfo(Authentication authentication);
    List<DoctorResponse> GetDoctorAvailability(LocalDate appointmentDate, TimeFrame timeFrame) throws UserMistake;

}
