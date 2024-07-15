package vietquan37.com.example.projects.service;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.DailyNoteDTO;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetRecordDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetServiceDTO;
import vietquan37.com.example.projects.payload.response.*;


import java.util.List;

public interface IHospitalizedPetService {
    void addHospitalizedPet(HospitalizedPetDTO hospitalizedPet) throws UserMistake;
    void updateHospitalizedPetForDoctor(Integer id,UpdatePetRecordDTO dto, Authentication authentication) throws OperationNotPermittedException, UserMistake;
    void updateServiceForPet(Integer id, UpdatePetServiceDTO dto) throws  UserMistake;
    void deleteServiceForPet(Integer id) throws  UserMistake;
    void addDailyNote(Integer id,DailyNoteDTO dto,Authentication authentication) throws OperationNotPermittedException, UserMistake;
    void updateDailyNote(Integer id,DailyNoteDTO dto,Authentication authentication) throws OperationNotPermittedException, UserMistake;
    Page<HospitalizedPetResponse> getAllForDoctor(int page,Authentication authentication);
    HospitalizedPetResponse getById(Integer id);
    Page<HospitalizedPetResponse> getAllForStaff(int page);
    Page<DailyNoteResponse>getAllDailyNotes(Integer id, Authentication authentication, int page) throws OperationNotPermittedException;
    List<HospitalizedPetResponse>  getAllForCustomer(Authentication authentication);
    Page<HospitalizedServiceResponse>getAllServiceById(Integer id,Authentication authentication,int page) throws OperationNotPermittedException;
    PaymentResponse payHospitalizedFee(Integer id,Authentication authentication) throws OperationNotPermittedException, PayPalRESTException, UserMistake;
    List<HospitalizedPetResponse> getAllHospitalizedPetByPetId(Authentication authentication, Integer id) throws OperationNotPermittedException;
    void dischargeHospitalizedPet(Integer id,Authentication authentication) throws OperationNotPermittedException, UserMistake;
    void deleteHospitalizedPet(Integer id) throws OperationNotPermittedException;
    DailyNoteResponse getDailyNoteById(Integer id,Authentication authentication) throws OperationNotPermittedException;

}
