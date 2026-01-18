package com.dhivakar.Library_Management_System.payload.response;

import com.dhivakar.Library_Management_System.payload.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String jwt;
    private String message;
    private String title;
    private UserDTO user;


}
