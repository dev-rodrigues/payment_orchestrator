package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.build.BillingBuilder;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import org.springframework.stereotype.Service;

@Service
public class Orchestrator {

    private final BillingRepository billingRepository;
    private final RabbitRepository rabbitRepository;

    public Orchestrator(BillingRepository repository, RabbitRepository rabbitRepository) {
        this.billingRepository = repository;
        this.rabbitRepository = rabbitRepository;
    }

    public void execute(PaymentRequest request) {
        var entity = BillingBuilder
                .builder()
                .withOrderId(request.orderId())
                .withPayment(request.paymentType())
                .withUser(request.userId())
                .withValue(request.value())
                .buildStarter();

        var saved = billingRepository.store(entity);

        rabbitRepository.notify(saved);
        rabbitRepository.sendToPay(saved);
    }
}
