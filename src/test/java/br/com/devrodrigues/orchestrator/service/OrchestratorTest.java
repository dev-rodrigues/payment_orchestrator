package br.com.devrodrigues.orchestrator.service;

import br.com.devrodrigues.orchestrator.core.ExternalResponse;
import br.com.devrodrigues.orchestrator.core.IntraQueue;
import br.com.devrodrigues.orchestrator.core.exceptions.BillingNotFoundException;
import br.com.devrodrigues.orchestrator.core.exceptions.ParseException;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.fixture.Fixture;
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

import static br.com.devrodrigues.orchestrator.fixture.Fixture.*;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {OrchestratorCoordinator.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "queue.intra.exchange=testValue",
        "queue.intra.payment.credit-card.routing-key=testValue",
        "queue.intra.payment.slip.routing-key=testValue",
        "queue.beta.response=testValue"
})
class OrchestratorTest {

    @MockBean
    BillingService service;

    @MockBean
    RabbitService rabbitService;

    @MockBean
    RabbitRepository rabbitRepository;

    @SpyBean
    OrchestratorCoordinator orchestrator;

    @Test
    void should_start_process() {

        when(service.createBilling(any())).thenReturn(completedFuture(getOrchestratorResponse()));
        when(service.persist(any())).thenReturn(completedFuture(getBillingEntity()));

        doAnswer((i) -> null).when(rabbitService).sendToExchange(any(), any());
        doAnswer((i) -> null).when(rabbitService).notifyRequester(any());


        orchestrator.start(getValidPaymentRequest());

        verify(service, times(1)).createBilling(any());
        verify(service, times(1)).persist(any());
        verify(rabbitService, times(1)).sendToExchange(any(), any());
    }

    @Test
    void should_not_start_process_when_fail_producer_exchange() {

        when(service.createBilling(any())).thenReturn(completedFuture(getOrchestratorResponse()));
        when(service.persist(any())).thenReturn(completedFuture(getBillingEntity()));

        doThrow(ParseException.class).when(rabbitService).sendToExchange(any(), any());

        orchestrator.start(getValidPaymentRequest());

        verify(rabbitService, times(1)).sendToExchange(any(), any());
        verify(rabbitService, times(0)).notifyRequester(any());
    }

    @Test
    void should_continue_a_process_when_valid_payment_response() {
        // given: a valid payment response
        var response = getValidPaymentResponse();

        // when: the orchestrator search for a billing
        when(service.getBillingById(any())).thenReturn(completedFuture(getBillingEntity()));

        // and: the orchestrator update the billing
        when(service.update(any(), any())).thenReturn(completedFuture(getBillingEntity()));

        // then: the orchestrator send a message to the requester
        doAnswer((i) -> null).when(rabbitService).notifyRequester(any());

        orchestrator.finish(response).thenRun(() -> {
            verify(service, times(1)).getBillingById(any());
            verify(service, times(1)).update(any(), any());
            verify(rabbitService, times(1)).notifyRequester(any());
        });
    }

    @Test
    void should_not_continue_a_process_when_invalid_payment_response() {
        // given: a invalid payment response
        var request = getValidPaymentResponse();

        // and: the orchestrator search for a billing and throw an billing not found exception
        doThrow(BillingNotFoundException.class).when(service).getBillingById(any());

        //then : the orchestrator not update the billing
        Assertions.assertThrows(BillingNotFoundException.class, () -> orchestrator.finish(request).thenRun(() -> {
            verify(service, times(1)).getBillingById(any());
            verify(service, times(0)).update(any(), any());
            verify(rabbitService, times(0)).notifyRequester(any());
        }));
    }

}