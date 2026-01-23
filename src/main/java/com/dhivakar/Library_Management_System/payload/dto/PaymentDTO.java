package com.dhivakar.Library_Management_System.payload.dto;


import com.dhivakar.Library_Management_System.domain.PaymentGateway;
import com.dhivakar.Library_Management_System.domain.PaymentStatus;
import com.dhivakar.Library_Management_System.domain.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Long id;

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    private String userName;

    private String userEmail;

    private Long bookLoanId;

    private Long subscriptionId;

    @NotNull(message = "Payment type is mandatory")
    private PaymentType paymentType;

    private PaymentStatus status;

    @NotNull(message = "Payment gateway is mandatory")
    private PaymentGateway gateway;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private Long amount;

//    @Size(min = 3, max = 3, message = "Currency must be 3-letter code")
//    private String currency;

    private String transactionId;

    private String gatewayPaymentId;

    private String gatewayOrderId;

    private String gatewaySignature;

//    private String paymentMethod;

    private String description;
    private String failureReason;
    private Integer retryCount;
    private LocalDateTime initiatedAt;
    private LocalDateTime completedAt;
//    private Boolean notificationSent;
//    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
