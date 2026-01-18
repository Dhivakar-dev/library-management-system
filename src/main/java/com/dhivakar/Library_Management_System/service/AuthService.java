package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.exception.UserException;
import com.dhivakar.Library_Management_System.payload.dto.UserDTO;
import com.dhivakar.Library_Management_System.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String username, String password) throws UserException;
    AuthResponse signup(UserDTO req) throws UserException;

    void createPasswordResetToken(String email) throws UserException;
    void resetPassword(String token, String newPassword) throws Exception;
}
