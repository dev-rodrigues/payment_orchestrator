package br.com.devrodrigues.orchestrator.service.middleware;

import br.com.devrodrigues.orchestrator.core.ExternalQueue;
import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.util.Objects.nonNull;

@Aspect
@Component
public class PaymentValidate {

    private final BillingRepository billingRepository;
    private final RabbitRepository rabbitRepository;

    public PaymentValidate(BillingRepository repository, RabbitRepository rabbitRepository) {
        this.billingRepository = repository;
        this.rabbitRepository = rabbitRepository;
    }

    @Around(
            "execution(* br.com.devrodrigues.orchestrator.service.Orchestrator.execute(..))"
    )
    public Object validate(ProceedingJoinPoint pjp) throws Throwable {
        var request = (PaymentRequest) pjp.getArgs()[0];

        var payment = billingRepository.findByOrderId(request.orderId());

        if (nonNull(payment) && !payment.isEmpty()) {
            var lastPayment = payment.get(payment.size() - 1);

            rabbitRepository.producerOnTopic(
                    new ExternalQueue(
                            "queue.beta.response",
                            lastPayment
                    )
            );
            return null;
        }

        return pjp.proceed();
    }
}