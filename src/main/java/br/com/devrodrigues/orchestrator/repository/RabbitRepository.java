package br.com.devrodrigues.orchestrator.repository;


import br.com.devrodrigues.orchestrator.core.IntraQueue;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RabbitRepository {
    void producer(IntraQueue intraQueue) throws JsonProcessingException;
}