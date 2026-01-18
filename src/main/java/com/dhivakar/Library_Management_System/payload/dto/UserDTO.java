package com.dhivakar.Library_Management_System.payload.dto;

import com.dhivakar.Library_Management_System.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    private String password;
    private String phone;

    @NotNull(message = "Full name is required")
    private String fullName;
    private UserRole role;
    private String username;

    private LocalDateTime lastLogin;
}
