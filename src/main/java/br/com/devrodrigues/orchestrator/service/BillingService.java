package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentResponse;
import br.com.devrodrigues.orchestrator.core.build.BillingBuilder;
import br.com.devrodrigues.orchestrator.core.exceptions.BillingNotFoundException;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
public class BillingService {

    @Value("${queue.intra.payment.credit-card.routing-key}")
    private String creditCardRoutingKey;

    @Value("${queue.intra.payment.slip.routing-key}")
    private String slipRoutingKey;

    private final BillingRepository repository;

    public BillingService(BillingRepository repository) {
        this.repository = repository;
    }

    public List<BillingEntity> findAll() {
        return repository.findAll();
    }

    public CompletionStage<Pair<BillingEntity, BillingBuilder>> createBilling(PaymentRequest request) {
        return supplyAsync(() ->
                BillingBuilder
                        .builder()
                        .possibleRouting(asList(creditCardRoutingKey, slipRoutingKey))
                        .withOrderId(request.orderId())
                        .withPayment(request.paymentType())
                        .withExchange()
                        .withUser(request.userId())
                        .withValue(request.value())
                        .buildStarter());
    }

    public CompletionStage<BillingEntity> persist(BillingEntity billingEntity) {
        return supplyAsync(() -> repository.store(billingEntity)).exceptionally((error) -> {
            System.out.println("Error: " + error.getMessage());
            return null;
        });
    }

    public CompletionStage<BillingEntity> getBillingById(Long billingId) {
        return supplyAsync(() -> {
            try {
                return repository.findById(billingId);
            } catch (Exception e) {
                throw new BillingNotFoundException(e);
            }
        }).exceptionally(throwable -> {
            throw new BillingNotFoundException(throwable);
        });
    }

    public CompletionStage<BillingEntity> update(BillingEntity billing, PaymentResponse response) {
        billing.setState(response.state());
        billing.setUpdatedAt(now());
        return supplyAsync(() -> repository.store(billing)).exceptionally((error) -> {
            System.out.println("Error: " + error.getMessage());
            return null;
        });
    }
}
