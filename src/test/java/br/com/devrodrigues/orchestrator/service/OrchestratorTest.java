package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.ExternalResponse;
import br.com.devrodrigues.orchestrator.core.IntraQueue;
import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentType;
import br.com.devrodrigues.orchestrator.core.build.BillingBuilder;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static br.com.devrodrigues.orchestrator.core.PaymentType.SLIP;
import static br.com.devrodrigues.orchestrator.fixture.Fixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {Orchestrator.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "queue.intra.exchange=testValue",
        "queue.intra.payment.credit-card.routing-key=testValue",
        "queue.intra.payment.slip.routing-key=testValue",
        "queue.beta.response=testValue"
})
class OrchestratorTest {

    @MockBean
    BillingRepository billingRepository;

    @MockBean
    RabbitRepository rabbitRepository;

    @SpyBean
    Orchestrator orchestrator;

    @Test
    void should_start_process() throws Exception {

        // given: a valid payment request
        var request = getValidPaymentRequest();

        // and: calling billing repository
        when(billingRepository.store(any(BillingEntity.class)))
                .thenReturn(getStartedBillingEntity());

        //when
        var response = orchestrator.startProcess(request);

        //then
        assertNotNull(response);
        assertEquals(BillingEntity.class, response.getFirst().getClass());
        assertEquals(BillingBuilder.class, response.getSecond().getClass());

        verify(billingRepository, times(1)).store(any(BillingEntity.class));
        verify(rabbitRepository, times(1)).producerOnExchange(any());
        verify(rabbitRepository, times(1)).producerOnTopic(any(ExternalResponse.class));
    }

    @Test
    void should_not_start_process_when_fail_producer_exchange() throws JsonProcessingException {

        // given: a valid payment request
        var request = getValidPaymentRequest();

        // and: calling billing repository
        when(billingRepository.store(any(BillingEntity.class)))
                .thenReturn(getStartedBillingEntity());

        // and: fail producer exchange
        doThrow(JsonProcessingException.class).when(rabbitRepository).producerOnExchange(any(IntraQueue.class));


        //then: fail
        assertThrows(JsonProcessingException.class, () -> orchestrator.startProcess(request));

        verify(rabbitRepository, times(0)).producerOnTopic(any(ExternalResponse.class));
    }

    @Test
    void should_continue_a_process_when_valid_payment_response() throws Exception {
        // given: a valid payment response
        var request = getValidPaymentResponse();

        // and: calling billing repository
        when(billingRepository.findById(any()))
                .thenReturn(getUpdatedBillingEntity());

        // and: calling billing repository
        when(billingRepository.store(any(BillingEntity.class)))
                .thenReturn(getUpdatedBillingEntity());

        //when: calling mediate process
        var response = orchestrator.mediateProcess(request);

        //then: calling producer on exchange
        verify(rabbitRepository, times(1)).producerOnTopic(any(ExternalResponse.class));

        //and: not null response
        assertNotNull(response);
    }

    @Test
    void should_not_continue_a_process_when_invalid_payment_response() throws Exception {
        // given: a invalid payment response
        var request = getValidPaymentResponse();

        // and: calling billing repository
        when(billingRepository.findById(any()))
                .thenReturn(null);

        // and: calling billing repository
        when(billingRepository.store(any(BillingEntity.class)))
                .thenReturn(null);


        //then: throwing exception
        assertThrows(RuntimeException.class, () -> orchestrator.mediateProcess(request));
    }
}