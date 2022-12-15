package br.com.devrodrigues.orchestrator.core;


import br.com.devrodrigues.orchestrator.core.objectsvalues.State;

import java.math.BigDecimal;
import java.util.Objects;

public record PaymentResponse(Long billingId, String orderId, String userId, BigDecimal value, State state,
                              BillingData billingData) {

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
