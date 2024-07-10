package vietquan37.com.example.projects.service;

import vietquan37.com.example.projects.enumClass.ServiceTypes;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.ServiceDTO;
import vietquan37.com.example.projects.payload.request.ServiceUpdateDTO;
import vietquan37.com.example.projects.payload.response.ServiceResponse;

import java.util.List;

public interface IService {
    void addService(ServiceDTO dto) throws UserMistake;
    List<ServiceResponse> getAllServices();
    List<ServiceResponse> getAllServicesForAdmin() ;
    void updateService(Integer id, ServiceUpdateDTO dto) throws UserMistake;
    void deleteService(Integer id);
    ServiceResponse getServiceById(Integer id);
    List<ServiceResponse> getAllServiceByType(ServiceTypes types);
    void unDeleteService(Integer id);

}
