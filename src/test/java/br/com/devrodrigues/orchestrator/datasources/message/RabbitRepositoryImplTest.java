package br.com.devrodrigues.orchestrator.datasources.message;

import br.com.devrodrigues.orchestrator.core.ExternalQueue;
import br.com.devrodrigues.orchestrator.core.ExternalResponse;
import br.com.devrodrigues.orchestrator.core.IntraQueue;
import br.com.devrodrigues.orchestrator.core.Response;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RabbitRepositoryImpl.class})
@ExtendWith(SpringExtension.class)
class RabbitRepositoryImplTest {

    @MockBean
    AmqpTemplate amqpTemplate;

    @MockBean
    ObjectMapper objectMapper;

    @SpyBean
    RabbitRepositoryImpl rabbitRepositoryImpl;

    @Test
    void should_produce_on_exchange_message() throws JsonProcessingException {
        // given: a valid message
        var message = new IntraQueue();

        // and: mapper json
        when(objectMapper.writeValueAsString(any(IntraQueue.class)))
                .thenReturn("json");

        // then: should produce message
        assertDoesNotThrow(() -> rabbitRepositoryImpl.producerOnExchange(message));
    }

    @Test
    void should_not_produce_on_exchange_message() throws JsonProcessingException {

        // given: a valid message
        var message = new IntraQueue("exchange_mock",
                "routing_key_mock",
                null
        );

        // mapper json
        when(objectMapper.writeValueAsString(any(IntraQueue.class)))
                .thenReturn("json");

        // and: amqp throw exception
        doThrow(AmqpException.class)
                .when(amqpTemplate)
                .convertAndSend(anyString(), anyString(), anyString());

        // then: throw exception
        assertThrows(AmqpException.class, () -> rabbitRepositoryImpl.producerOnExchange(message));
    }

    @Test
    void should_proce_external_queue_on_topic() throws JsonProcessingException {
        // given: a valid message
        var message = new ExternalQueue(
                "queue_name_mock",
                new BillingEntity()
        );


        // and: mapper json
        when(objectMapper.writeValueAsString(any(ExternalQueue.class)))
                .thenReturn("json");

        // then: should produce message
        assertDoesNotThrow(() -> rabbitRepositoryImpl.producerOnTopic(message));
    }

    @Test
    void should_proce_external_response_on_topic() throws JsonProcessingException {
        // given: a valid message
        var message = new ExternalResponse(
                "queue_name_mock",
                new Response()
        );


        // and: mapper json
        when(objectMapper.writeValueAsString(any(ExternalQueue.class)))
                .thenReturn("json");

        // then: should produce message
        assertDoesNotThrow(() -> rabbitRepositoryImpl.producerOnTopic(message));
    }
}