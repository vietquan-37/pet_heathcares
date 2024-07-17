package vietquan37.com.example.projects.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vietquan37.com.example.projects.entity.*;

import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetRecordDTO;
import vietquan37.com.example.projects.payload.response.DailyNoteResponse;

import vietquan37.com.example.projects.payload.response.HospitalizedPetResponse;
import vietquan37.com.example.projects.payload.response.HospitalizedServiceResponse;


@Mapper(componentModel = "spring")
public interface HospitalizedPetMapper {

    @Mapping(target = "admissionDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "status", constant = "UNDER_TREATMENT")
    @Mapping(target = "paid", constant = "false")
    HospitalizedPet mapDto(HospitalizedPetDTO dto);


    @Mapping(target = "diagnosis", source = "diagnosis")
    @Mapping(target = "treatment", source = "treatment")
    void mapUpdatePetRecord(UpdatePetRecordDTO dto, @MappingTarget HospitalizedPet existingPet);

    @Mapping(target = "id", source = "id")

    @Mapping(target = "diagnosis", source = "diagnosis")
    @Mapping(target = "treatment", source = "treatment")
    @Mapping(target = "admissionDate", source = "admissionDate")
    @Mapping(target = "dischargeDate", source = "dischargeDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "cageNumber", source = "cage.cageNumber")
    @Mapping(target = "petName", source = "pet.name")
    HospitalizedPetResponse mapForDoctor(HospitalizedPet pet);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "diagnosis", source = "diagnosis")
    @Mapping(target = "treatment", source = "treatment")
    @Mapping(target = "admissionDate", source = "admissionDate")
    @Mapping(target = "dischargeDate", source = "dischargeDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "cageNumber", source = "cage.cageNumber")
    @Mapping(target = "doctorName", source = "doctor.user.fullName")
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "paid", source = "paid")
    HospitalizedPetResponse mapForResponse(HospitalizedPet pet);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "serviceName", source = "service.name")
    @Mapping(target = "price", source = "service.price")
    @Mapping(target = "dateUsage", source = "usageDate")
    HospitalizedServiceResponse mapForService(HospitalizedPetServices pet);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "date", source = "date")
    DailyNoteResponse mapForDailyNotes(DailyNote note);




}



