package br.com.devrodrigues.orchestrator.datasources.message;

import br.com.devrodrigues.orchestrator.core.ExternalQueue;
import br.com.devrodrigues.orchestrator.core.ExternalResponse;
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

        String json = getJson(intraQueue);

        this.amqpTemplate.convertAndSend(
                intraQueue.getExchangeName(),
                intraQueue.getRoutingKey(),
                json
        );
    }

    @Override
    public void producerOnTopic(ExternalQueue externalQueue) throws JsonProcessingException {
        var json = getJson(externalQueue.messageData());
        this.amqpTemplate.convertAndSend(externalQueue.queueName(), json);
    }

    @Override
    public void producerOnTopic(ExternalResponse externalQueue) throws JsonProcessingException {
        var json = getJson(externalQueue.getResponse());
        this.amqpTemplate.convertAndSend(externalQueue.getQueueName(), json);
    }

    private String getJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}