package com.dhivakar.Library_Management_System.service;

import com.dhivakar.Library_Management_System.domain.FineStatus;
import com.dhivakar.Library_Management_System.domain.FineType;
import com.dhivakar.Library_Management_System.payload.dto.FineDTO;
import com.dhivakar.Library_Management_System.payload.request.CreateFineRequest;
import com.dhivakar.Library_Management_System.payload.request.WaiveFineRequest;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;
import com.dhivakar.Library_Management_System.payload.response.PaymentInitiateResponse;

import java.util.List;

public interface FineService {

    FineDTO createFine(CreateFineRequest createFineRequest);

    PaymentInitiateResponse payFine(Long fineId, String transactionId) throws Exception;

    void markFineAsPaid(Long fineId, Long amount, String transactionId) throws Exception;

    FineDTO waiveFine(WaiveFineRequest waiveFineRequest) throws Exception;

    List<FineDTO> getMyFines(FineStatus status, FineType type) throws Exception;

    PageResponse<FineDTO> getAllFines(
            FineStatus status,
            FineType type,
            Long userId,
            int page,
            int size
    );
}
