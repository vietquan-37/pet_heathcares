package vietquan37.com.example.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.payload.request.ServiceDTO;
import vietquan37.com.example.projects.payload.response.ServiceResponse;
@Mapper(componentModel = "spring")
public interface ServiceMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deleted", constant = "false")
    Services mapDto(ServiceDTO dto);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "deleted", source = "deleted")
    ServiceResponse mapToAllServiceDto(Services service);
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void updateServiceFromDto(ServiceDTO dto, @MappingTarget Services service);
}
