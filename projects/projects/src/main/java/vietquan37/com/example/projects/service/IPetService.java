package vietquan37.com.example.projects.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.payload.request.PetDTO;
import vietquan37.com.example.projects.payload.response.PetResponse;

import java.util.List;

public interface IPetService {
    void CreatePet(PetDTO dto, Authentication connectedUser) ;
    Page<PetResponse>GetAllPets(int page);
    List<PetResponse> GetAllPetsByUser( Authentication connectedUser);
    void UpdatePet(Integer id,PetDTO dto, Authentication connectedUser) throws OperationNotPermittedException;
    void DeletePet(Integer id, Authentication connectedUser) throws OperationNotPermittedException;
}
