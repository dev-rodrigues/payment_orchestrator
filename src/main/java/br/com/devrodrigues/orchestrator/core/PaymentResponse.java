package br.com.devrodrigues.orchestrator.core;


import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public final class PaymentResponse {
    private final UUID billingId;
    private final String orderId;
    private final String userId;
    private final BigDecimal value;
    private final State state;
    private final BillingData billingData;

    public PaymentResponse(
            UUID billingId,
            String orderId,
            String userId,
            BigDecimal value,
            State state,
            BillingData billingData
    ) {
        this.billingId = billingId;
        this.orderId = orderId;
        this.userId = userId;
        this.value = value;
        this.state = state;
        this.billingData = billingData;
    }

    public UUID billingId() {
        return billingId;
    }

    public String orderId() {
        return orderId;
    }

    public String userId() {
        return userId;
    }

    public BigDecimal value() {
        return value;
    }

    public State state() {
        return state;
    }

    public BillingData billingData() {
        return billingData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PaymentResponse) obj;
        return Objects.equals(this.billingId, that.billingId) &&
                Objects.equals(this.orderId, that.orderId) &&
                Objects.equals(this.userId, that.userId) &&
                Objects.equals(this.value, that.value) &&
                Objects.equals(this.state, that.state) &&
                Objects.equals(this.billingData, that.billingData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billingId, orderId, userId, value, state, billingData);
    }

    @Override
    public String toString() {
        return "PaymentResponse[" +
                "billingId=" + billingId + ", " +
                "orderId=" + orderId + ", " +
                "userId=" + userId + ", " +
                "value=" + value + ", " +
                "state=" + state + ", " +
                "billingData=" + billingData + ']';
    }

}
