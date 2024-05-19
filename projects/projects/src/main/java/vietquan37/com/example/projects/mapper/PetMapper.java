package vietquan37.com.example.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vietquan37.com.example.projects.entity.Pet;
import vietquan37.com.example.projects.payload.request.PetDTO;
import vietquan37.com.example.projects.payload.response.PetResponse;

@Mapper(componentModel = "spring")
public interface PetMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "species", source = "species")
    @Mapping(target = "gender", source = "gender")
    @Mapping(source = "birthDate", target = "birthDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "deleted", constant = "false")
    Pet mapDto(PetDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "species", source = "species")
    @Mapping(target = "gender", source = "gender")
    @Mapping(source = "birthDate", target = "birthDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "ownerName", source = "customer.user.fullName")
    PetResponse mapToPetResponse(Pet pet);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "species", source = "species")
    @Mapping(target = "gender", source = "gender")
    @Mapping(source = "birthDate", target = "birthDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Pet mapUpdateDto(PetDTO dto, @MappingTarget Pet existingPet);
}
