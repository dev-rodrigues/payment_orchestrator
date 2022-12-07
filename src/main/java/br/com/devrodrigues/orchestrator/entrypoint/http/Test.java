package br.com.devrodrigues.orchestrator.entrypoint.http;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentType;
import br.com.devrodrigues.orchestrator.service.Orchestrator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/test")
public class Test {

    private final Orchestrator orchestrator;

    public Test(Orchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @GetMapping
    public void test() throws JsonProcessingException {
        orchestrator.startProcess(
                new PaymentRequest(
                        "12345",
                        PaymentType.SLIP,
                        "123",
                        BigDecimal.ONE
                )
        );
    }
}
