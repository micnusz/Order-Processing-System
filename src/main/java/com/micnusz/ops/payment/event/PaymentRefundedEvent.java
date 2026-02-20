package com.micnusz.ops.payment.event;

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
public class PaymentRefundedEvent {
    private UUID orderId;
    private UUID paymentId;
    private BigDecimal amount;
    private String reason;
    private Instant refundedAt;
}