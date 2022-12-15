package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentResponse;
import br.com.devrodrigues.orchestrator.core.build.BillingBuilder;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletionStage;

@Service
public class OrchestratorCoordinator {
    private final BillingService service;
    private final RabbitService rabbitService;


    public OrchestratorCoordinator(BillingService service,
                                   RabbitService rabbitService) {
        this.service = service;
        this.rabbitService = rabbitService;
    }

    public CompletionStage<Pair<BillingEntity, BillingBuilder>> start(PaymentRequest request) {

        return
                // start to create the billing
                service.createBilling(request)
                        .thenCompose((created) ->

                        // register the billing on database
                        service.persist(created.getFirst())
                                .thenCompose((persisted) ->

                                        // send to exchange for direct routing to the payment service
                                        rabbitService.sendToExchange(created.getSecond().getRoutingKey(), persisted)
                                                .thenCompose((exchange) ->

                                                        // notify the requester that started the process
                                                        rabbitService.notifyRequester(persisted)
                                                                .thenApply((topic) ->
                                                                        Pair.of(persisted, created.getSecond())))));
    }

    public CompletionStage<BillingEntity> finish(PaymentResponse response) {
        return
                service.getBillingById(response.billingId())
                        .thenCompose((billing) ->
                                service.update(billing, response)
                                        .thenCompose((updated) ->
                                                rabbitService.notifyRequester(updated)
                                                        .thenApply((topic) -> updated)))
                        .exceptionally((error) -> {
                            System.out.println("Error: " + error.getMessage());
                            return null;
                        });
    }
}