package br.com.devrodrigues.orchestrator.datasources.message;

import br.com.devrodrigues.orchestrator.core.ExternalQueue;
import br.com.devrodrigues.orchestrator.core.IntraQueue;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitRepositoryImpl implements RabbitRepository {

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper mapper;


    public RabbitRepositoryImpl(AmqpTemplate amqpTemplate, ObjectMapper mapper) {
        this.amqpTemplate = amqpTemplate;
        this.mapper = mapper;
    }

    @Override
    public void producerOnExchange(IntraQueue intraQueue) throws JsonProcessingException {
        var json = mapper.writeValueAsString(intraQueue.messageData());

        this.amqpTemplate.convertAndSend(
                intraQueue.exchangeName(),
                intraQueue.routingKey(),
                json
        );
    }

    @Override
    public void producerOnTopic(ExternalQueue externalQueue) throws JsonProcessingException {
        var json = mapper.writeValueAsString(externalQueue.messageData());
        this.amqpTemplate.convertAndSend(externalQueue.queueName(), json);
    }
}