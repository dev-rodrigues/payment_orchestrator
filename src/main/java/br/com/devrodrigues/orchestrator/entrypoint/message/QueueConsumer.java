package br.com.devrodrigues.orchestrator.entrypoint.message;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentResponse;
import br.com.devrodrigues.orchestrator.service.OrchestratorCoordinator;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {
    private final OrchestratorCoordinator coordinator;
    private final Gson gson;

    public QueueConsumer(OrchestratorCoordinator coordinator) {
        this.coordinator = coordinator;
        gson = new Gson();
    }

    @RabbitListener(queues = {"${queue.beta.request}"})
    public void receiveExternal(@Payload String fileBody) {
        var paymentRequest = gson.fromJson(fileBody, PaymentRequest.class);

        coordinator.start(
                paymentRequest
        ).thenRun(() -> {
            System.out.println("Started orchestrator");
        });
    }

    @RabbitListener(queues = {"${queue.intra.payment.result.name}"})
    public void receiveInternal(@Payload String fileBody) {
        var paymentResponse = gson.fromJson(fileBody, PaymentResponse.class);
        coordinator.finish(paymentResponse).thenRun(() -> {
            System.out.println("Finished orchestrator");
        });
    }
}