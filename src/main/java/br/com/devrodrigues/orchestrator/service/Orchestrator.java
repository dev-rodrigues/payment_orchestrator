package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.build.BillingBuilder;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import org.springframework.stereotype.Service;

@Service
public class Orchestrator {

    private final BillingRepository repository;

    public Orchestrator(BillingRepository repository) {
        this.repository = repository;
    }

    public void execute(PaymentRequest request) {
        var entity = BillingBuilder
                .builder()
                .withOrderId(request.orderId())
                .withPayment(request.paymentType())
                .withUser(request.userId())
                .withValue(request.value())
                .buildStarter();

        var saved = repository.store(entity);
    }
}
