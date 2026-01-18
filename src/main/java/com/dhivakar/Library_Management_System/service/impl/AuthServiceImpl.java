package com.dhivakar.Library_Management_System.service.impl;

import com.dhivakar.Library_Management_System.configuration.JwtProvider;
import com.dhivakar.Library_Management_System.domain.UserRole;
import com.dhivakar.Library_Management_System.exception.UserException;
import com.dhivakar.Library_Management_System.mapper.UserMapper;
import com.dhivakar.Library_Management_System.modal.PasswordResetToken;
import com.dhivakar.Library_Management_System.modal.User;
import com.dhivakar.Library_Management_System.payload.dto.UserDTO;
import com.dhivakar.Library_Management_System.payload.response.AuthResponse;
import com.dhivakar.Library_Management_System.repository.PasswordResetTokenRepository;
import com.dhivakar.Library_Management_System.repository.UserRepository;
import com.dhivakar.Library_Management_System.service.AuthService;
import com.dhivakar.Library_Management_System.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserServiceImplementation customUserServiceImplementation;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;


    @Override
    public AuthResponse login(String username, String password) throws UserException {
        Authentication authentication = authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        String role = authorities.iterator().next().getAuthority();

        String token = jwtProvider.generateToken(authentication);

        User user = userRepository.findByEmail(username);

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setTitle("Login success");
        response.setMessage("Welcome Back" + username);
        response.setJwt(token);
        response.setUser(UserMapper.toDTO(user));

        return response;
    }

    private Authentication authenticate(String username, String password) throws UserException {

        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(username);

        if(userDetails == null) {
            throw new UserException("user not found with email - " + password);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("password not match");
        }
        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }

    @Override
    public AuthResponse signup(UserDTO req) throws UserException {


        User user = userRepository.findByEmail(req.getEmail());

        if(user != null) {
            throw new UserException("email id already registered");
        }

        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setPassword(passwordEncoder.encode(req.getPassword()));
        createdUser.setPhone(req.getPhone());
        createdUser.setFullName(req.getFullName());
        createdUser.setLastLogin(LocalDateTime.now());
        createdUser.setRole(UserRole.ROLE_USER);

        User savedUser = userRepository.save(createdUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtProvider.generateToken(auth);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setTitle("Welcome " + createdUser.getFullName());
        response.setMessage("register success");
        response.setUser(UserMapper.toDTO(savedUser));
        return response;
    }

    @Transactional
    public void createPasswordResetToken(String email) throws UserException {

        String frontendUrl = "http://localhost:5173";
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UserException("user not found with given email");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        passwordResetTokenRepository.save(resetToken);
        String resetLink = frontendUrl + token;
        String subject = "Password Reset Request";
        String body = "You requested to reset your password. Use this link (valid 5 minutes): " + resetLink;

        //send email
        emailService.sendEmail(user.getEmail(), subject, body);

    }

    @Transactional
    public void resetPassword(String token, String newPassword) throws Exception {

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)

                .orElseThrow(
                        () -> new Exception("token not valid!")
                );

        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            throw new Exception("token expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);

    }
}
