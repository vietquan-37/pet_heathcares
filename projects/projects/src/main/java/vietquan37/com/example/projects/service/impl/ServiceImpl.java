package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.enumClass.ServiceTypes;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.ServiceMapper;
import vietquan37.com.example.projects.payload.request.ServiceDTO;
import vietquan37.com.example.projects.payload.request.ServiceUpdateDTO;
import vietquan37.com.example.projects.payload.response.ServiceResponse;
import vietquan37.com.example.projects.repository.ServiceRepository;
import vietquan37.com.example.projects.service.IService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceImpl implements IService {
    private final ServiceMapper mapper;
    private final ServiceRepository serviceRepository;

    @Override
    public void addService(ServiceDTO dto) throws UserMistake {
        if (serviceRepository.findByName(dto.getName()).isPresent()) {
            throw new UserMistake("the service name has been created before");
        }
        Services service = mapper.mapDto(dto);
        serviceRepository.save(service);
    }

    @Override
    public List<ServiceResponse> getAllServices() {
        List<Services> services = serviceRepository.findAllByDeletedIsFalse();
        return services.stream().map(mapper::mapToAllServiceDto).collect(Collectors.toList());
    }

    @Override
    public List<ServiceResponse> getAllServicesForAdmin() {
        List<Services> services = serviceRepository.findAll();
        return services.stream().map(mapper::mapToAllServiceDto).collect(Collectors.toList());
    }

    @Override
    public void updateService(Integer id, ServiceUpdateDTO dto) throws UserMistake {
        Services service = serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("service not found"));
        if (!dto.getName().equals(service.getName()) && serviceRepository.findByName(dto.getName()).isPresent()) {
            throw new UserMistake("the name have been used by another service");
        }
        mapper.updateServiceFromDto(dto, service);
        serviceRepository.save(service);

    }

    @Override
    public void deleteService(Integer id) {
        Services service = serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("service not found"));
        service.setDeleted(true);
        serviceRepository.save(service);
    }

    @Override
    public ServiceResponse getServiceById(Integer id) {
        Services service = serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("service not found"));
        return mapper.mapToAllServiceDto(service);
    }

    @Override
    public List<ServiceResponse> getAllServiceByType(ServiceTypes types) {
        List<Services>services=serviceRepository.findAllByDeletedIsFalseAndType(types);
        return services.stream().map(mapper::mapToAllServiceDtoForUser).collect(Collectors.toList());
    }
}
