package vietquan37.com.example.projects.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.entity.Cage;
import vietquan37.com.example.projects.enumClass.CageStatus;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.CageMapper;
import vietquan37.com.example.projects.payload.request.CageDTO;
import vietquan37.com.example.projects.payload.response.CageResponse;
import vietquan37.com.example.projects.repository.CageRepository;
import vietquan37.com.example.projects.repository.HospitalizedPetRepository;
import vietquan37.com.example.projects.service.ICageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CageService implements ICageService {
    private final CageRepository cageRepository;
    private final CageMapper cageMapper;
    private final HospitalizedPetRepository hospitalizedPetRepository;

    @Override
    public void addCage(CageDTO cage) throws UserMistake {
        if (cageRepository.findByCageNumber(cage.getCageNumber()) != null) {
            throw new UserMistake("cage number can not be duplicated");
        }
        Cage entity = cageMapper.map(cage);
        cageRepository.save(entity);
    }

    @Override
    public void updateCage(Integer id, CageDTO dto) throws UserMistake {
        Cage cage = cageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("cage not found"));

        if (cage.getCageNumber() != dto.getCageNumber() && cageRepository.findByCageNumber(dto.getCageNumber()) != null) {
            throw new UserMistake("cage number can not be duplicated");
        }
        cageMapper.updateMap(dto, cage);
        cageRepository.save(cage);
    }

    @Override
    public void deleteCage(Integer id) throws UserMistake {
        Cage cage = cageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("cage not found"));
        if(hospitalizedPetRepository.countHospitalizedPetByCageIdAndDischargeDateAndDeletedIsFalse(id,null)>0){
            throw new UserMistake("This cage has been used by hospitalized pets");
        }
        cage.setDeleted(true);
        cageRepository.save(cage);
    }

    @Override
    public List<CageResponse> getAllCage() {
        List<Cage> cages = cageRepository.findAll();
        return cages.stream().map(cageMapper::mapResponse).collect(Collectors.toList());
    }

    @Override
    public List<CageResponse> getAllCageForStaff() {
        List<Cage> cages = cageRepository.findAllByDeletedIsFalseAndCageStatus(CageStatus.Available);
        return cages.stream().map(cageMapper::mapResponse).collect(Collectors.toList());
    }

    @Override
    public void unDeleteCage(Integer id) {
        Cage cage = cageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("cage not found"));
        cage.setDeleted(false);
        cageRepository.save(cage);
    }


    @Override
    public CageResponse getCageById(Integer id) {
        Cage cage = cageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("cage not found"));
        return cageMapper.mapResponse(cage);
    }
}
