package br.com.devrodrigues.orchestrator.repository;


import br.com.devrodrigues.orchestrator.core.IntraQueue;

public interface RabbitRepository {
    void producer(IntraQueue intraQueue);
}