package br.com.devrodrigues.orchestrator.core;

public enum PaymentType {
    SLIP,
    CREDIT_CARD;

    public static PaymentType fromString(String state) {
        return PaymentType.valueOf(state.toUpperCase());
    }
}
