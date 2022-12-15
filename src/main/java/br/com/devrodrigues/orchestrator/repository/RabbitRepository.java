package br.com.devrodrigues.orchestrator.repository;


import br.com.devrodrigues.orchestrator.core.ExternalQueue;
import br.com.devrodrigues.orchestrator.core.ExternalResponse;
import br.com.devrodrigues.orchestrator.core.IntraQueue;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RabbitRepository {
    void producerOnExchange(IntraQueue intraQueue) throws JsonProcessingException;
    void producerOnTopic(ExternalQueue externalQueue) throws JsonProcessingException;
    void producerOnTopic(ExternalResponse externalQueue) throws JsonProcessingException;
}