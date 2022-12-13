package br.com.devrodrigues.orchestrator.entrypoint.http;

import br.com.devrodrigues.orchestrator.openapi.api.StartedApi;
import br.com.devrodrigues.orchestrator.openapi.model.Billing;
import br.com.devrodrigues.orchestrator.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StartedApiImpl implements StartedApi {

    private final BillingService service;

    public StartedApiImpl(BillingService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<Billing>> startedGet() {
        var response = service.findAll();
        var result = response
                .stream()
                .map(it ->
                        new Billing().id(it.getId().toString())
                                .orderId(it.getOrderId())
                                .state(it.getState().name())
                                .userId(it.getUserId())
                                .paymentType(it.getType().name())
                ).toList();

        return ResponseEntity.ok(result);
    }
}
