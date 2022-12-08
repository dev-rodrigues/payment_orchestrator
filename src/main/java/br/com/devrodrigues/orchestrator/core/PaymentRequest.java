package br.com.devrodrigues.orchestrator.core;

import java.math.BigDecimal;

public class PaymentRequest {
    private final String orderId;
    private final PaymentType paymentType;
    private final String userId;
    private final BigDecimal value;

    public PaymentRequest(String orderId, PaymentType paymentType, String userId, BigDecimal value) {
        this.orderId = orderId;
        this.paymentType = paymentType;
        this.userId = userId;
        this.value = value;
    }

    public String orderId() {
        return orderId;
    }

    public PaymentType paymentType() {
        return paymentType;
    }

    public String userId() {
        return userId;
    }

    public BigDecimal value() {
        return value;
    }
}
