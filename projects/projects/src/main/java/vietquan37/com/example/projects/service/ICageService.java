package vietquan37.com.example.projects.service;

import vietquan37.com.example.projects.entity.Cage;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.payload.request.CageDTO;
import vietquan37.com.example.projects.payload.response.CageResponse;

import java.util.List;

public interface ICageService {
    void addCage(CageDTO cage) throws UserMistake;
    void updateCage(Integer id,CageDTO cage) throws UserMistake;
    void deleteCage(Integer id) throws UserMistake;
    CageResponse getCageById(Integer id) ;
    List<CageResponse> getAllCage();
    List<CageResponse> getAllCageForStaff();

}
