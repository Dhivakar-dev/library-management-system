package com.dhivakar.Library_Management_System.controller;


import com.dhivakar.Library_Management_System.exception.SubscriptionException;
import com.dhivakar.Library_Management_System.payload.dto.SubscriptionDTO;
import com.dhivakar.Library_Management_System.payload.response.ApiResponse;
import com.dhivakar.Library_Management_System.payload.response.PaymentInitiateResponse;
import com.dhivakar.Library_Management_System.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    // remove @valid for postman testing
    public ResponseEntity<?> subscribe(
             @RequestBody SubscriptionDTO subscription
    ) throws Exception {
        PaymentInitiateResponse dto = subscriptionService.subscribe(subscription);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/user/active")
    public ResponseEntity<?> getUsersActiveSubscription(
            @RequestParam(required=false) Long userId
    ) throws Exception {
        SubscriptionDTO dto = subscriptionService
                .getUsersActiveSubscription(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllSubscriptions() {
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);
        List<SubscriptionDTO> dtoList = subscriptionService.getAllSubscriptions(pageable);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/admin/deactivate-expired")
    public ResponseEntity<?> deactivateExpiredSubscriptions() throws Exception {
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);
        subscriptionService.deactivateExpiredSubscriptions();
        ApiResponse apiResponse = new ApiResponse("Expired subscriptions deactivated successfully", true);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/cancel/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(
            @PathVariable Long subscriptionId,
            @RequestParam(required = false) String reason) throws SubscriptionException {

        SubscriptionDTO subscription = subscriptionService
                .cancelSubscription(subscriptionId, reason);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateSubscription(
            @RequestParam Long subscriptionId,
            @RequestParam Long paymentId) throws SubscriptionException {
        SubscriptionDTO subscription = subscriptionService
                .activateSubscription(subscriptionId, paymentId);
        return ResponseEntity.ok(subscription);
    }
}
