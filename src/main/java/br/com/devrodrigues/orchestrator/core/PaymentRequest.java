package br.com.devrodrigues.orchestrator.core;

import java.math.BigDecimal;

public record PaymentRequest(String orderId, PaymentType paymentType, String userId, BigDecimal value) {
}
