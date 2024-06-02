package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import vietquan37.com.example.projects.config.CloudinaryService;
import vietquan37.com.example.projects.entity.Customer;
import vietquan37.com.example.projects.entity.Pet;

import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.enumClass.Role;
import vietquan37.com.example.projects.exception.FileException;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.PetMapper;

import vietquan37.com.example.projects.payload.request.PetDTO;
import vietquan37.com.example.projects.payload.response.PetResponse;
import vietquan37.com.example.projects.repository.AppointmentRepository;
import vietquan37.com.example.projects.repository.CustomerRepository;
import vietquan37.com.example.projects.repository.HospitalizedPetRepository;
import vietquan37.com.example.projects.repository.PetRepository;

import vietquan37.com.example.projects.service.IPetService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PetService implements IPetService {
    private final CustomerRepository customerRepository;
    private final CloudinaryService cloudinaryService;
    private final PetRepository petRepository;
    private final PetMapper mapper;
    private static final int MAX = 5;
    private final HospitalizedPetRepository hospitalizedPetRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void CreatePet(PetDTO dto, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pet pet = mapper.mapDto(dto);
        Customer customer = customerRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        pet.setCustomer(customer);
        petRepository.save(pet);
    }

    @Override
    public Page<PetResponse> GetAllPets(int page) {
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<Pet> userPage = petRepository.findAllByDeletedIsFalse(pageable);
        return userPage.map(mapper::mapToPetResponse);
    }

    @Override
    public List<PetResponse> GetAllPetsByUser(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Customer customer = customerRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        List<Pet> pets = petRepository.findAllByCustomerAndDeletedIsFalse(customer);

        return pets.stream()
                .map(mapper::mapToPetResponseForUser)
                .collect(Collectors.toList());
    }

    @Override
    public PetResponse GetUserPetById(Integer id, Authentication connectedUser) throws OperationNotPermittedException {
        Pet pet = petRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(pet.getCustomer().getUser().getId(), user.getId()) && user.getRole() != Role.ADMIN) {
            throw new OperationNotPermittedException("You are not allowed to view that pet");
        }
        return mapper.mapToPetResponseForUser(pet);
    }

    @Override
    public void UploadImage(Integer id, MultipartFile image, Authentication connectedUser) throws OperationNotPermittedException, FileException, IOException {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(pet.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to update that pet");
        }
        if (image != null && !image.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(image);
            if (pet.getImageUrl() != null && !pet.getImageUrl().isEmpty()) {
                cloudinaryService.deleteFile(pet.getImageUrl());
            }
            pet.setImageUrl(imageUrl);
            petRepository.save(pet);
        }
    }

    @Override
    public void UpdatePet(Integer id, PetDTO dto, Authentication connectedUser) throws OperationNotPermittedException {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(pet.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to update that pet");
        }

        mapper.mapUpdateDto(dto, pet);
        petRepository.save(pet);
    }


    @Override
    public void DeletePet(Integer id, Authentication connectedUser) throws OperationNotPermittedException, UserMistake {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet not found"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(pet.getCustomer().getUser().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not allow to delete that pet");
        }
        if(hospitalizedPetRepository.countHospitalizedPetByCageIdAndDischargeDate(id,null)>0||appointmentRepository.countAppointmentByPetIdAndAppointmentDateAfter(id, LocalDate.now())>0){
            throw new UserMistake("This pet has been in valid booking or take care by us");
        }
        pet.setDeleted(true);
        petRepository.save(pet);
    }
}
