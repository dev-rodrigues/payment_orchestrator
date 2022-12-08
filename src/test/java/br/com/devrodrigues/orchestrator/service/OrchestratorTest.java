package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.BillingData;
import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentResponse;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static br.com.devrodrigues.orchestrator.core.PaymentType.SLIP;
import static br.com.devrodrigues.orchestrator.core.State.PROCESSED;
import static br.com.devrodrigues.orchestrator.core.State.PROCESSING;
import static java.math.BigDecimal.ONE;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {Orchestrator.class})
@TestPropertySource(properties = {
       "queue.intra.exchange=dummy",
        "queue.intra.payment.credit-card.routing-key=dummy",
        "queue.intra.payment.slip.routing-key=dummy",
        "queue.beta.response=dummy"
})
@ExtendWith(SpringExtension.class)
class OrchestratorTest {

    @SpyBean
    Orchestrator orchestrator;

    @MockBean
    BillingRepository billingRepository;

    @MockBean
    RabbitRepository rabbitRepository;

    @MockBean
    ObjectMapper mapper;

//    @Test
//    void should_start_process() throws Exception {
//        var result = orchestrator.startProcess(
//                new PaymentRequest(
//                        "dummy",
//                        SLIP,
//                        "dummy",
//                        ONE
//                )
//        );
//
//        verify(rabbitRepository, times(1)).producerOnExchange(any());
////        verify(rabbitRepository, times(1)).producerOnTopic(any());
//        assertNotNull(result);
//    }

    @Test
    void should_not_start_process_when_throws_exceptions() throws JsonProcessingException {
        doThrow(JsonProcessingException.class)
                .when(rabbitRepository)
                .producerOnExchange(any());

        assertThrows(JsonProcessingException.class, ()-> orchestrator.startProcess(
                new PaymentRequest(
                        "123",
                        SLIP,
                        "user",
                        ONE
                )
        ));

//        verify(rabbitRepository, times(0)).producerOnTopic(any());
    }

    @Test
    void should_not_update_when_billing_does_not_exists() {
        when(billingRepository.findById(any())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> orchestrator.mediateProcess(
                new PaymentResponse(randomUUID(), "dummy", "dummy", ONE, PROCESSING, new BillingData(
                        "dummy", "dummy", "dummy", "dummy", "dummy"
                ))
        ));
    }

//    @Test
//    void should_update_status_when_billing_exists() throws JsonProcessingException {
//        when(billingRepository.findById(any())).thenReturn(new BillingEntity());
//        orchestrator.mediateProcess(new PaymentResponse(
//                UUID.randomUUID(),
//                "dummy",
//                "dummy",
//                ONE,
//                PROCESSED,
//                new BillingData(
//                        "dummy",
//                        "dummy",
//                        "dummy",
//                        "dummy",
//                        "dummy"
//                )
//        ));
////        verify(rabbitRepository, times(1)).producerOnTopic(any());
//    }
}
