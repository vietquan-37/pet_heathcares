package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vietquan37.com.example.projects.entity.HospitalizedPet;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.enumClass.ServiceTypes;
import vietquan37.com.example.projects.mapper.HospitalizedPetMapper;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.repository.DoctorRepository;
import vietquan37.com.example.projects.repository.HospitalizedPetRepository;
import vietquan37.com.example.projects.repository.PetRepository;
import vietquan37.com.example.projects.repository.ServiceRepository;
import vietquan37.com.example.projects.service.IHospitalizedPetService;

import java.time.LocalDate;
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
    @Override
    public void addHospitalizedPet(HospitalizedPetDTO dto) {
        var pet = petRepository.findById(dto.getPetId()).orElseThrow(()->
                new EntityNotFoundException("pet not found"));
        var doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow(()->
                new EntityNotFoundException("doctor not found"));
        List<Services> services = dto.getServiceIds().stream()
                .map(serviceId -> serviceRepository.findByIdAndDeletedIsFalseAndType(serviceId, ServiceTypes.HOSPITALIZATION)
                        .orElseThrow(() -> new EntityNotFoundException("service not found")))
                .toList();
        Map<Services, LocalDate> serviceUsage = services.stream()
                .collect(Collectors.toMap(service -> service, service -> LocalDate.now()));
        HospitalizedPet hospitalizedPet = mapper.mapDto(dto);
        hospitalizedPet.setPet(pet);
        hospitalizedPet.setDoctor(doctor);
        hospitalizedPet.setServices(services);
        hospitalizedPet.setServiceUsage(serviceUsage);
        repository.save(hospitalizedPet);
    }
}
