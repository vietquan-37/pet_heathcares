package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vietquan37.com.example.projects.config.CloudinaryService;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.exception.FileException;
import vietquan37.com.example.projects.mapper.DoctorMapper;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.response.DoctorResponse;
import vietquan37.com.example.projects.repository.DoctorRepository;
import vietquan37.com.example.projects.service.IDoctorService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final CloudinaryService cloudinaryService;


    @Override
    public void UpdateDoctor(Integer id, DoctorDTO dto) {
        Doctor doctor=doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        doctorMapper.doctorDTOToDoctor(dto,doctor);
        doctorRepository.save(doctor);
    }

    @Override
    public List<DoctorResponse> GetAllDoctors() {
       var doctors= doctorRepository.findAllByUser_AccountLockedFalse();
       return doctors.stream()
               .map(doctorMapper::mapDoctorResponse).collect(Collectors.toList());
    }

    @Override
    public void UploadImage(Integer id, MultipartFile image) throws FileException, IOException {
        Doctor doctor=doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        if (image != null && !image.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(image);
            if (doctor.getImageUrl() != null && !doctor.getImageUrl().isEmpty()) {
                cloudinaryService.deleteFile(doctor.getImageUrl());
            }
            doctor.setImageUrl(imageUrl);
            doctorRepository.save(doctor);
        }
    }

    @Override
    public List<DoctorResponse> GetAllDoctorForAdmin() {
        var doctors= doctorRepository.findAll();
        return doctors.stream().map(doctorMapper::mapDoctorResponseForAdmin).collect(Collectors.toList());
    }

    @Override
    public DoctorResponse GetDoctorById(Integer id) {
        Doctor doctor=doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        return doctorMapper.mapDoctorResponseForAdmin(doctor);

    }
}
