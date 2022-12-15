package br.com.devrodrigues.orchestrator.core.build;

import br.com.devrodrigues.orchestrator.core.PaymentType;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static br.com.devrodrigues.orchestrator.datasources.database.entity.BillingType.of;
import static br.com.devrodrigues.orchestrator.core.State.WAITING;
import static java.time.LocalDateTime.now;

public class BillingBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String userId;
    private String orderId;
    private PaymentType paymentType;
    private BigDecimal value;

    private String routingKey;
    private List<String> possibleRouting;

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

    public BillingBuilder possibleRouting(List<String> asList) {
        this.possibleRouting = asList;
        return this;
    }

    public BillingBuilder withExchange() {
        if (Objects.isNull(this.paymentType)) {
            throw new IllegalStateException("paymentType is required");
        }

        switch (this.paymentType) {
            case CREDIT_CARD -> this.routingKey = this.possibleRouting.get(0);
            case SLIP -> this.routingKey = this.possibleRouting.get(1);
            default -> throw new IllegalStateException("Unexpected value: " + this.paymentType);
        }

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

    public CompletionStage<Pair<BillingEntity, BillingBuilder>> build() {
        return CompletableFuture.supplyAsync(this::buildStarter);
    }

    public Pair<BillingEntity, BillingBuilder> buildStarter() {

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
                value,
                now(),
                now()
        );

        logger.info("Billing starter created: {}", billingEntity);

        return Pair.of(billingEntity, this);
    }

    public String getRoutingKey() {
        return routingKey;
    }
}