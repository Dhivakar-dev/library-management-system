package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.payload.dto.PaymentDTO;
import com.dhivakar.Library_Management_System.payload.request.PaymentInitiateRequest;
import com.dhivakar.Library_Management_System.payload.request.PaymentVerifyRequest;
import com.dhivakar.Library_Management_System.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) throws Exception;

    PaymentDTO verifyPayment(PaymentVerifyRequest req) throws Exception;

    Page<PaymentDTO> getAllPayments(Pageable pageable);
}
