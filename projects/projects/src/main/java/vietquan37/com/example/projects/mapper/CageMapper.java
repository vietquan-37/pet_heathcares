package vietquan37.com.example.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vietquan37.com.example.projects.entity.Cage;
import vietquan37.com.example.projects.payload.request.CageDTO;
import vietquan37.com.example.projects.payload.response.CageResponse;


@Mapper(componentModel = "spring")
public interface CageMapper {
    @Mapping(target = "cageNumber", source = "cageNumber")
    @Mapping(target = "cageStatus", source = "cageStatus")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deleted", constant = "false")
    Cage map(CageDTO cageDTO);
    @Mapping(target = "cageNumber", source = "cageNumber")
    @Mapping(target = "cageStatus", source = "cageStatus")
    @Mapping(target = "updatedAt",expression ="java(java.time.LocalDateTime.now())" )
    void updateMap(CageDTO DTO, @MappingTarget Cage cage);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cageNumber", source = "cageNumber")
    @Mapping(target = "cageStatus", source = "cageStatus")
    @Mapping(target = "updatedAt",source = "updatedAt")
    @Mapping(target = "createdAt",source = "createdAt")
    @Mapping(target = "deleted", source = "deleted")
    CageResponse mapResponse(Cage cage);
}
