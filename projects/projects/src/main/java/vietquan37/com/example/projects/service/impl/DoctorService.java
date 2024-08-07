package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vietquan37.com.example.projects.config.CloudinaryService;
import vietquan37.com.example.projects.entity.Doctor;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.enumClass.TimeFrame;
import vietquan37.com.example.projects.enumClass.WorkingDay;
import vietquan37.com.example.projects.exception.FileException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.DoctorMapper;
import vietquan37.com.example.projects.payload.request.DoctorDTO;
import vietquan37.com.example.projects.payload.request.DoctorSearchDTO;
import vietquan37.com.example.projects.payload.response.DoctorResponse;
import vietquan37.com.example.projects.repository.AppointmentRepository;
import vietquan37.com.example.projects.repository.DoctorRepository;
import vietquan37.com.example.projects.service.IDoctorService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final CloudinaryService cloudinaryService;
    private final AppointmentRepository appointmentRepository;


    @Override
    public void UpdateDoctor(Integer id, DoctorDTO dto) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        doctorMapper.doctorDTOToDoctor(dto, doctor);
        doctorRepository.save(doctor);
    }

    @Override
    public List<DoctorResponse> GetAllDoctors() {
        var doctors = doctorRepository.findAllByUser_AccountLockedFalse();
        return doctors.stream().map(doctorMapper::mapDoctorResponseForInfo).collect(Collectors.toList());
    }

    @Override
    public void UploadImage(Integer id, MultipartFile image) throws FileException, IOException {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
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
        var doctors = doctorRepository.findAll();
        return doctors.stream().map(doctorMapper::mapDoctorResponseForInfo).collect(Collectors.toList());
    }

    @Override
    public DoctorResponse GetDoctorById(Integer id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        return doctorMapper.mapDoctorResponseForInfo(doctor);

    }

    @Override
    public DoctorResponse GetDoctorInfo(Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        var doctor = doctorRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        return doctorMapper.mapDoctorResponseForInfo(doctor);
    }

    @Override
    public List<DoctorResponse> GetDoctorAvailability(LocalDate appointmentDate, TimeFrame timeFrame) throws UserMistake {
        if (appointmentDate.isBefore(LocalDate.now())) {
            throw new UserMistake("can not choose a past date");
        }
        WorkingDay appointmentDay = WorkingDay.valueOf(appointmentDate.getDayOfWeek().name());
        var doctor = doctorRepository.findAllBySpecificWorkingDayAndUser_AccountLockedFalse(appointmentDay.name());
        doctor.removeIf(doc -> appointmentRepository.countAppointmentByDoctorIdAndTimeFrameAndAppointmentDateAndAppointmentStatus(doc.getId(), timeFrame,appointmentDate,AppointmentStatus.BOOKED) >= 3);

        return doctor.stream().map(doctorMapper::mapDoctorResponseForInfo).collect(Collectors.toList());
    }
}
