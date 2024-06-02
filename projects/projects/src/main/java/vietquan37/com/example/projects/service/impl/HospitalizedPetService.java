package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vietquan37.com.example.projects.entity.HospitalizedPet;
import vietquan37.com.example.projects.entity.HospitalizedPetServices;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.enumClass.ServiceTypes;
import vietquan37.com.example.projects.mapper.HospitalizedPetMapper;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.repository.*;
import vietquan37.com.example.projects.service.IHospitalizedPetService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalizedPetService implements IHospitalizedPetService {
    private final HospitalizedPetRepository repository;
    private final HospitalizedPetMapper mapper;
    private final PetRepository petRepository;
    private final DoctorRepository doctorRepository;
    private final ServiceRepository serviceRepository;
    private final HospitalizedPetServiceRepository hospitalizedPetServiceRepository;

    @Override
    public void addHospitalizedPet(HospitalizedPetDTO dto) {
        var pet = petRepository.findById(dto.getPetId()).orElseThrow(() ->
                new EntityNotFoundException("pet not found"));
        var doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow(() ->
                new EntityNotFoundException("doctor not found"));
        HospitalizedPet hospitalizedPet = mapper.mapDto(dto);
        hospitalizedPet.setPet(pet);
        hospitalizedPet.setDoctor(doctor);
        repository.save(hospitalizedPet);
       List<HospitalizedPetServices>hospitalizedPetServices=new ArrayList<>();
        for (Integer serviceId : dto.getServiceIds()) {
            Services service = serviceRepository.findById(serviceId).orElseThrow(() ->
                    new EntityNotFoundException("service not found"));
         HospitalizedPetServices hospitalizedPetService = new HospitalizedPetServices();
            hospitalizedPetService.setHospitalizedPet(hospitalizedPet);
            hospitalizedPetService.setService(service);
            hospitalizedPetService.setUsageDate(LocalDate.now());
            hospitalizedPetServices.add(hospitalizedPetService);
        }
        hospitalizedPetServiceRepository.saveAll(hospitalizedPetServices);
    }
}
