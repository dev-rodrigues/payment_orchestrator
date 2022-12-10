package br.com.devrodrigues.orchestrator.entrypoint.message;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {QueueConsumer.class})
class QueueConsumerTest {

    @SpyBean
    QueueConsumer consumer;

    @Test
    void should_receive_valid_external_request() {
        // given: a valid external request
        // when: receive external request
        // then: should call orchestrator
    }
}