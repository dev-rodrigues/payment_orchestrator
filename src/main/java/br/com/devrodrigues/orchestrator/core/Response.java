package br.com.devrodrigues.orchestrator.core;

import java.util.Objects;

public class Response {
    private String orderId;
    private String paymentStatus;

    public Response() {
    }

    public Response(
            String orderId,
            String paymentStatus
    ) {
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
