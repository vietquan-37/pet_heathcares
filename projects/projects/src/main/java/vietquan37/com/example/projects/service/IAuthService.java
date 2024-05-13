package vietquan37.com.example.projects.service;

import jakarta.mail.MessagingException;

import vietquan37.com.example.projects.exception.EmailAlreadyExistsException;
import vietquan37.com.example.projects.payload.request.LoginDTO;
import vietquan37.com.example.projects.payload.request.RegisterDTO;
import vietquan37.com.example.projects.payload.response.AuthenticationResponse;


import java.io.UnsupportedEncodingException;

public interface IAuthService {
    void register(RegisterDTO registerDTO) throws EmailAlreadyExistsException, MessagingException, UnsupportedEncodingException;
     void VerifyEmail( String token) throws MessagingException, UnsupportedEncodingException;
     AuthenticationResponse authenticate(LoginDTO request) ;
}
