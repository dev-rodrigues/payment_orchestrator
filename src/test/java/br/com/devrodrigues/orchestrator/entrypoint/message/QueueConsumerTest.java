package br.com.devrodrigues.orchestrator.entrypoint.message;

import br.com.devrodrigues.orchestrator.service.Orchestrator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.devrodrigues.orchestrator.fixture.Fixture.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {QueueConsumer.class})
@ExtendWith(SpringExtension.class)
class QueueConsumerTest {

    @MockBean
    Orchestrator orchestrator;

    @SpyBean
    QueueConsumer consumer;

    @Test
    void should_receive_valid_payment_request() throws JsonProcessingException {
        // given: a valid external request
        var json = getValidJSONPaymentRequest();

        // when:
        when(orchestrator.startProcess(any())).thenReturn(getOrchestratorResponse());

        // then:
        assertDoesNotThrow(() -> consumer.receiveExternal(json));
    }

    @Test
    void should_receive_valid_payment_response() throws JsonProcessingException {
        // given: a valid external request
        var json = getValidJSONPaymentResponse();

        // when:
        when(orchestrator.mediateProcess(any())).thenReturn(getBillingEntity());

        // then:
        assertDoesNotThrow(() -> consumer.receiveInternal(json));
    }
}