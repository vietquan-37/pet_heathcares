package vietquan37.com.example.projects.service;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import vietquan37.com.example.projects.exception.FileException;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.PetDTO;
import vietquan37.com.example.projects.payload.response.PetResponse;

import java.io.IOException;
import java.util.List;

public interface IPetService {
    void CreatePet(PetDTO dto, Authentication connectedUser);
    Page<PetResponse>GetAllPets(int page);
    List<PetResponse> GetAllPetsByUser( Authentication connectedUser);
    void UpdatePet(Integer id,PetDTO dto, Authentication connectedUser) throws OperationNotPermittedException;
    void DeletePet(Integer id, Authentication connectedUser) throws OperationNotPermittedException, UserMistake;
    PetResponse GetUserPetById(Integer id, Authentication connectedUser) throws OperationNotPermittedException;
    void UploadImage(Integer id,MultipartFile image, Authentication connectedUser) throws OperationNotPermittedException, FileException, IOException;
}
