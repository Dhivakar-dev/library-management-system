package com.dhivakar.Library_Management_System.service.impl;

import com.dhivakar.Library_Management_System.domain.UserRole;
import com.dhivakar.Library_Management_System.modal.User;
import com.dhivakar.Library_Management_System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void initializeAdminUser(){
        String adminEmail = "dhivakarkmechskct@gmail.com";
        String adminPassword = "testpwdadmin";

        if(userRepository.findByEmail(adminEmail) == null){
            User user = User.builder()
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .fullName("Dhivakar")
                    .role(UserRole.ROLE_ADMIN)
                    .build();

            User admin = userRepository.save(user);
        }
    }



}
