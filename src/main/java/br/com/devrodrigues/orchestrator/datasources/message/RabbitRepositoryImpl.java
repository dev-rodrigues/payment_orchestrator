package br.com.devrodrigues.orchestrator.datasources.message;

import br.com.devrodrigues.orchestrator.core.IntraQueue;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitRepositoryImpl implements RabbitRepository {

    private final AmqpTemplate amqpTemplate;

    public RabbitRepositoryImpl(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void producer(IntraQueue intraQueue) {
        this.amqpTemplate.convertAndSend(
                intraQueue.exchangeName(),
                intraQueue.routingKey(),
                intraQueue.messageData()
        );
    }
}