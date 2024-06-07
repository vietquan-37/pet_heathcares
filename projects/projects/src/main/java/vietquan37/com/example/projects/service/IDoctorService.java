package vietquan37.com.example.projects.service;

import org.springframework.web.multipart.MultipartFile;
import vietquan37.com.example.projects.exception.FileException;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.response.DoctorResponse;

import java.io.IOException;
import java.util.List;

public interface IDoctorService {
    void UpdateDoctor(Integer id, DoctorDTO dto);
    List<DoctorResponse> GetAllDoctors();
    void UploadImage(Integer id, MultipartFile image) throws FileException, IOException;
    List<DoctorResponse> GetAllDoctorForAdmin();
    DoctorResponse GetDoctorById(Integer id);
}
