package com.micnusz.ops.payment.dto;

import com.micnusz.ops.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private PaymentStatus status;
    private String paymentMethod;
    private String transactionId;
    private String failureReason;
    private Instant createdAt;
    private Instant processedAt;
}