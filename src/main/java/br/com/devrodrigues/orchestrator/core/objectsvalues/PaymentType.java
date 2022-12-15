package br.com.devrodrigues.orchestrator.core.objectsvalues;

public enum PaymentType {
    SLIP,
    CREDIT_CARD;

    public static PaymentType fromString(String state) {
        return PaymentType.valueOf(state.toUpperCase());
    }
}
