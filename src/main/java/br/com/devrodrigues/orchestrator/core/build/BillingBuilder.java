package br.com.devrodrigues.orchestrator.core.build;

import br.com.devrodrigues.orchestrator.core.PaymentType;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static br.com.devrodrigues.orchestrator.datasources.database.entity.BillingType.of;
import static br.com.devrodrigues.orchestrator.datasources.database.entity.States.WAITING;
import static java.time.LocalDateTime.now;

public class BillingBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String userId;
    private String orderId;
    private PaymentType paymentType;
    private BigDecimal value;

    private BillingBuilder() {
    }

    public static BillingBuilder builder() {
        return new BillingBuilder();
    }

    public BillingBuilder withUser(String userId) {
        this.userId = userId;
        return this;
    }

    public BillingBuilder withPayment(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public BillingBuilder withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public BillingBuilder withValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public BillingEntity buildStarter() {

        if (userId == null) {
            throw new IllegalStateException("User is required");
        }

        if (paymentType == null) {
            throw new IllegalStateException("Payment type is required");
        }

        if (value == null) {
            throw new IllegalStateException("Value is required");
        }

        var billingEntity = new BillingEntity(
                this.userId,
                this.orderId,
                WAITING,
                of(paymentType.name()),
                now(),
                now()
        );

        logger.info("Billing starter created: {}", billingEntity);

        return billingEntity;
    }
}