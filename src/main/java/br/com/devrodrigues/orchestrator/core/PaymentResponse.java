package br.com.devrodrigues.orchestrator.core;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(
        UUID billingId,
        String orderId,
        String userId,
        BigDecimal value,
        State state,
        BillingData billingData
) {
}
