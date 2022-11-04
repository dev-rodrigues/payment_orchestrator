package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.IntraQueue;
import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.build.BillingBuilder;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.util.Arrays.asList;

@Service
public class Orchestrator {

    @Value("${queue.intra.exchange}")
    private String exchangeName;

    @Value("${queue.intra.payment.credit-card.routing-key}")
    private String creditCardRoutingKey;

    @Value("${queue.intra.payment.slip.routing-key}")
    private String slipRoutingKey;

    private final Gson gson;
    private final BillingRepository billingRepository;
    private final RabbitRepository rabbitRepository;

    public Orchestrator(Gson gson, BillingRepository repository, RabbitRepository rabbitRepository) {
        this.gson = gson;
        this.billingRepository = repository;
        this.rabbitRepository = rabbitRepository;

    }

    public void execute(PaymentRequest request) {
        var entity = BillingBuilder
                .builder(gson)
                .possibleRoutings(asList(creditCardRoutingKey, slipRoutingKey))
                .withOrderId(request.orderId())
                .withPayment(request.paymentType())
                .withExchange()
                .withUser(request.userId())
                .withValue(request.value())
                .buildStarter();

        var saved = billingRepository.store(entity.getFirst());

        rabbitRepository.producer(
                new IntraQueue(
                        exchangeName,
                        entity.getSecond().getRoutingKey(),
                        gson.toJson(saved)
                )
        );
    }
}
