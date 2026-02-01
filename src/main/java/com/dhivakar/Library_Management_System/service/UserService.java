package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.modal.User;
import com.dhivakar.Library_Management_System.payload.dto.UserDTO;

import java.util.List;

public interface UserService {

    public User getCurrentUser() throws Exception;
    public List<UserDTO> getAllUsers();
    User findById(Long id) throws Exception;
}
