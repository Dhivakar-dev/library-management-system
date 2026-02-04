package com.dhivakar.Library_Management_System.service.impl;

import com.dhivakar.Library_Management_System.domain.FineStatus;
import com.dhivakar.Library_Management_System.domain.FineType;
import com.dhivakar.Library_Management_System.domain.PaymentGateway;
import com.dhivakar.Library_Management_System.domain.PaymentType;
import com.dhivakar.Library_Management_System.mapper.FineMapper;
import com.dhivakar.Library_Management_System.modal.BookLoan;
import com.dhivakar.Library_Management_System.modal.Fine;
import com.dhivakar.Library_Management_System.modal.User;
import com.dhivakar.Library_Management_System.payload.dto.FineDTO;
import com.dhivakar.Library_Management_System.payload.request.CreateFineRequest;
import com.dhivakar.Library_Management_System.payload.request.PaymentInitiateRequest;
import com.dhivakar.Library_Management_System.payload.request.WaiveFineRequest;
import com.dhivakar.Library_Management_System.payload.response.PageResponse;
import com.dhivakar.Library_Management_System.payload.response.PaymentInitiateResponse;
import com.dhivakar.Library_Management_System.repository.BookLoanRepository;
import com.dhivakar.Library_Management_System.repository.FineRepository;
import com.dhivakar.Library_Management_System.service.FineService;
import com.dhivakar.Library_Management_System.service.PaymentService;
import com.dhivakar.Library_Management_System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final BookLoanRepository bookLoanRepository;
    private final FineMapper fineMapper;
    private final UserService userService;
    private final PaymentService paymentService;
    @Override
    public FineDTO createFine(CreateFineRequest createFineRequest) {

//     1. validate book loan exist
        BookLoan bookLoan = bookLoanRepository.findById(createFineRequest.getBookLoanId())
                .orElseThrow(() -> new RuntimeException("Book loan doesn't exist"));

        Fine fine = Fine.builder()
                .bookLoan(bookLoan)
                .user(bookLoan.getUser())
                .type(createFineRequest.getType())
                .amount(createFineRequest.getAmount())
                .status(FineStatus.PENDING)
                .reason(createFineRequest.getReason())
                .notes(createFineRequest.getNotes())
                .build();

        Fine savedFine = fineRepository.save(fine);
        return fineMapper.toDTO(savedFine);
    }

    @Override
    public PaymentInitiateResponse payFine(Long fineId, String transactionId) throws Exception {

        // 1. validate fine exist
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new Exception("Fine doesn't exist"));


        // 2. check already paid
        if(fine.getStatus().equals(FineStatus.PAID)){
            throw new Exception("fine already paid");
        }
        if(fine.getStatus().equals(FineStatus.WAIVED)){
            throw new Exception("fine waived");
        }

        // 3. initiate payment

        User user = userService.getCurrentUser();

        PaymentInitiateRequest request = PaymentInitiateRequest
                .builder()
                .userId(user.getId())
                .fineId(fine.getId())
                .paymentType(PaymentType.FINE)
                .gateway(PaymentGateway.RAZORPAY)
                .amount(fine.getAmount())
                .description("library fine payment")
                .build();
        return paymentService.initiatePayment(request);


    }

    @Override
    public void markFineAsPaid(Long fineId, Long amount, String transactionId) throws Exception {

        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new Exception(
                        "Fine not found with id: " + fineId));

        // Apply payment amount safely
        fine.applyPayment(amount);
        fine.setTransactionId(transactionId);
        fine.setStatus(FineStatus.PAID);
        fine.setUpdatedAt(LocalDateTime.now());

        fineRepository.save(fine);

    }

    @Override
    public FineDTO waiveFine(WaiveFineRequest waiveFineRequest) throws Exception {

        Fine fine = fineRepository.findById(waiveFineRequest.getFineId())
                .orElseThrow(() -> new Exception("Fine not found with id: "+ waiveFineRequest.getFineId()));

        // 2. Check if already waived or paid
        if (fine.getStatus() == FineStatus.WAIVED) {
            throw new Exception("Fine has already been waived");
        }

        if (fine.getStatus() == FineStatus.PAID) {
            throw new Exception("Fine has already been paid and cannot be waived");
        }


        // 3. Waive the fine
        User currentAdmin = userService.getCurrentUser();
        fine.waive(currentAdmin, waiveFineRequest.getReason());

        // 4. Save and return
        Fine savedFine = fineRepository.save(fine);

        return fineMapper.toDTO(savedFine);
    }

    @Override
    public List<FineDTO> getMyFines(FineStatus status, FineType type) throws Exception {

        User currentUser = userService.getCurrentUser();
        List<Fine> fines;

        // Apply filters based on parameters
        if (status != null && type != null) {
            // Both filters
            fines = fineRepository.findByUserId(currentUser.getId()).stream()
                    .filter(f -> f.getStatus() == status && f.getType() == type)
                    .collect(Collectors.toList());
        } else if (status != null) {
            // Status filter only
            fines = fineRepository.findByUserId(currentUser.getId()).stream()
                    .filter(f -> f.getStatus() == status)
                    .collect(Collectors.toList());
        } else if (type != null) {
            // Type filter only
            fines = fineRepository.findByUserIdAndType(currentUser.getId(), type);
        } else {
            // No filter - all fines for user
            fines = fineRepository.findByUserId(currentUser.getId());
        }


        return fines.stream().map(
                fineMapper::toDTO
        ).collect(Collectors.toList());
    }

    @Override
    public PageResponse<FineDTO> getAllFines(FineStatus status, FineType type, Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending());

        Page<Fine> finePage = fineRepository.findAllWithFilters(
                userId,
                status,
                type,
                pageable
        );


        return convertToPageResponse(finePage);
    }

    private PageResponse<FineDTO> convertToPageResponse(Page<Fine> finePage) {
        List<FineDTO> fineDTOs = finePage.getContent() // List<Fine>
                .stream() // Stream<Fine>
                .map(fineMapper::toDTO) // Stream<FineDTO>
                .collect(Collectors.toList());

        return new PageResponse<>(
                fineDTOs,
                finePage.getNumber(),
                finePage.getSize(),
                finePage.getTotalElements(),
                finePage.getTotalPages(),
                finePage.isLast(),
                finePage.isFirst(),
                finePage.isEmpty()
        );
    }
}
