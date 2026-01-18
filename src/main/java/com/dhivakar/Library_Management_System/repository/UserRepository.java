package com.dhivakar.Library_Management_System.repository;

import com.dhivakar.Library_Management_System.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
