package com.dhivakar.Library_Management_System.mapper;

import com.dhivakar.Library_Management_System.modal.Payment;
import com.dhivakar.Library_Management_System.payload.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    public PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());

        // User information
        if (payment.getUser() != null) {
            dto.setUserId(payment.getUser().getId());
            dto.setUserName(payment.getUser().getFullName());
            dto.setUserEmail(payment.getUser().getEmail());
        }

//        // Book loan information
//        if (payment.getBookLoan() != null) {
//            dto.setBookLoanId(payment.getBookLoan().getId());
//        }

        // Subscription information
        if (payment.getSubscription() != null) {
            dto.setSubscriptionId(payment.getSubscription().getId());
        }

        dto.setPaymentType(payment.getPaymentType());
        dto.setStatus(payment.getStatus());
        dto.setGateway(payment.getGateway());
        dto.setAmount(payment.getAmount());

        dto.setTransactionId(payment.getTransactionId());
        dto.setGatewayPaymentId(payment.getGatewayPaymentId());
        dto.setGatewayOrderId(payment.getGatewayOrderId());
        dto.setGatewaySignature(payment.getGatewaySignature());

        dto.setDescription(payment.getDescription());
        dto.setFailureReason(payment.getFailureReason());

        dto.setInitiatedAt(payment.getInitiatedAt());
        dto.setCompletedAt(payment.getCompletedAt());

//        dto.setNotificationSent(payment.getNotificationSent());
//        dto.setActive(payment.getActive());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setUpdatedAt(payment.getUpdatedAt());

        return dto;
    }

}
