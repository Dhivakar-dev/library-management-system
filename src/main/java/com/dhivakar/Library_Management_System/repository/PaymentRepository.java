package com.dhivakar.Library_Management_System.repository;

import com.dhivakar.Library_Management_System.modal.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
