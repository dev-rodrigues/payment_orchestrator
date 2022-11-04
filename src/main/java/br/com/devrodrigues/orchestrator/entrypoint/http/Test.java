package br.com.devrodrigues.orchestrator.entrypoint.http;

import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentType;
import br.com.devrodrigues.orchestrator.service.Orchestrator;
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
    public void test() {
        orchestrator.execute(
                new PaymentRequest(
                        "123",
                        PaymentType.SLIP,
                        "carlos",
                        BigDecimal.ONE
                )
        );
    }
}
