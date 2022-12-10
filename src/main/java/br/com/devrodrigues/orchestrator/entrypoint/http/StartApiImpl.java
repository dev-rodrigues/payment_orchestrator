package br.com.devrodrigues.orchestrator.entrypoint.http;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentType;
import br.com.devrodrigues.orchestrator.openapi.api.StartApi;
import br.com.devrodrigues.orchestrator.openapi.model.Request;
import br.com.devrodrigues.orchestrator.service.Orchestrator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class StartApiImpl implements StartApi {

    private final Orchestrator orchestrator;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public StartApiImpl(Orchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @Override
    public ResponseEntity<Void> startPost(Request request) {

        try {
            var response = orchestrator.startProcess(
                    new PaymentRequest(
                            request.getOrderId(),
                            PaymentType.fromString(request.getPaymentType().getValue()),
                            request.getUserId(),
                            BigDecimal.valueOf(request.getValue())
                    )
            );

            logger.info("Response: {}", response);

        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
