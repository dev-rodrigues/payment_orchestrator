package br.com.devrodrigues.orchestrator.entrypoint.http;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentType;
import br.com.devrodrigues.orchestrator.openapi.api.StartApi;
import br.com.devrodrigues.orchestrator.openapi.model.Request;
import br.com.devrodrigues.orchestrator.service.OrchestratorCoordinator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class StartApiImpl implements StartApi {
    private final OrchestratorCoordinator orchestratorCoordinator;
    public StartApiImpl(OrchestratorCoordinator orchestratorCoordinator) {
        this.orchestratorCoordinator = orchestratorCoordinator;
    }

    @Override
    public ResponseEntity<Void> startPost(Request request) {
        orchestratorCoordinator.start(
                new PaymentRequest(
                        request.getOrderId(),
                        PaymentType.fromString(request.getPaymentType().getValue()),
                        request.getUserId(),
                        BigDecimal.valueOf(request.getValue())
                )
        ).thenRun(() -> {
            System.out.println("Started orchestrator");
        });
        return ResponseEntity.noContent().build();
    }
}