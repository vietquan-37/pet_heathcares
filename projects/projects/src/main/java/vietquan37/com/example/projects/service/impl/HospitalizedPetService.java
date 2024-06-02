package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vietquan37.com.example.projects.entity.HospitalizedPet;
import vietquan37.com.example.projects.entity.HospitalizedPetServices;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.enumClass.CageStatus;
import vietquan37.com.example.projects.enumClass.ServiceTypes;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.HospitalizedPetMapper;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.repository.*;
import vietquan37.com.example.projects.service.IHospitalizedPetService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalizedPetService implements IHospitalizedPetService {
    private final HospitalizedPetRepository repository;
    private final HospitalizedPetMapper mapper;
    private final PetRepository petRepository;
    private final DoctorRepository doctorRepository;
    private final ServiceRepository serviceRepository;
    private final HospitalizedPetServiceRepository hospitalizedPetServiceRepository;
    private final CageRepository cageRepository;

    @Override
    public void addHospitalizedPet(HospitalizedPetDTO dto) {
        var pet = petRepository.findById(dto.getPetId()).orElseThrow(() ->
                new EntityNotFoundException("pet not found"));
        var doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow(() ->
                new EntityNotFoundException("doctor not found"));
        var cage = cageRepository.findByIdAndDeletedIsFalseAndCageStatus(dto.getCageId(), CageStatus.Available).orElseThrow(() -> new EntityNotFoundException("cage not found or it has been occupied"));
        HospitalizedPet hospitalizedPet = mapper.mapDto(dto);
        hospitalizedPet.setPet(pet);
        hospitalizedPet.setCage(cage);
        hospitalizedPet.setDoctor(doctor);
        repository.save(hospitalizedPet);
        List<HospitalizedPetServices> hospitalizedPetServices = new ArrayList<>();
        for (Integer serviceId : dto.getServiceIds()) {
            Services service = serviceRepository.findByIdAndDeletedIsFalseAndType(serviceId,ServiceTypes.HOSPITALIZATION).orElseThrow(() ->
                    new EntityNotFoundException("service not found"));
            HospitalizedPetServices hospitalizedPetService = new HospitalizedPetServices();
            hospitalizedPetService.setHospitalizedPet(hospitalizedPet);
            hospitalizedPetService.setService(service);
            hospitalizedPetService.setUsageDate(LocalDate.now());
            hospitalizedPetServices.add(hospitalizedPetService);
        }
        hospitalizedPetServiceRepository.saveAll(hospitalizedPetServices);
        int currentOccupancy = repository.countHospitalizedPetByCageIdAndDischargeDate(cage.getId(), null);
        if (currentOccupancy >= cage.getCapacity()) {
            cage.setCageStatus(CageStatus.Occupied);
            cageRepository.save(cage);
        }
    }
}
