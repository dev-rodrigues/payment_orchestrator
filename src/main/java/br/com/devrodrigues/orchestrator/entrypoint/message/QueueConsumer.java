package br.com.devrodrigues.orchestrator.entrypoint.message;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentResponse;
import br.com.devrodrigues.orchestrator.service.Orchestrator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    private final Orchestrator orchestrator;
    private final Gson gson;

    public QueueConsumer(Orchestrator orchestrator) {
        this.orchestrator = orchestrator;
        gson = new Gson();
    }

    @RabbitListener(queues = {"${queue.beta.request}"})
    public void receiveExternal(@Payload String fileBody) throws JsonProcessingException {
        var paymentRequest = gson.fromJson(fileBody, PaymentRequest.class);
        orchestrator.startProcess(paymentRequest);
    }

    @RabbitListener(queues = {"${queue.intra.payment.result.name}"})
    public void receiveInternal(@Payload String fileBody) throws JsonProcessingException {
        var paymentResponse = gson.fromJson(fileBody, PaymentResponse.class);
        orchestrator.mediateProcess(paymentResponse);
    }
}