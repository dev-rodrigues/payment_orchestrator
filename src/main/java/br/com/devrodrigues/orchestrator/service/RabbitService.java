package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.ExternalResponse;
import br.com.devrodrigues.orchestrator.core.IntraQueue;
import br.com.devrodrigues.orchestrator.core.Response;
import br.com.devrodrigues.orchestrator.core.exceptions.ParseException;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.runAsync;

@Service
public class RabbitService {

    @Value("${queue.intra.exchange}")
    private String exchangeName;

    @Value("${queue.beta.response}")
    private String external;

    private final RabbitRepository rabbitRepository;

    public RabbitService(RabbitRepository rabbitRepository) {
        this.rabbitRepository = rabbitRepository;
    }


    public CompletionStage<Void> sendToExchange(String routingKey, BillingEntity persisted) throws ParseException{
        return runAsync(() -> {
            try {
                rabbitRepository.producerOnExchange(
                        new IntraQueue(exchangeName, routingKey, persisted)
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(throwable -> {
            throw new ParseException(throwable);
        });
    }

    public CompletionStage<Void> notifyRequester(BillingEntity persisted) {
        return runAsync(() -> {
            try {
                rabbitRepository.producerOnTopic(
                        new ExternalResponse(
                                external,
                                new Response(persisted.getOrderId(), persisted.getState().name())
                        )
                );
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> System.out.println("Message sending to: " + external));
    }
}
