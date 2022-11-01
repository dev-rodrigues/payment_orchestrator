package br.com.devrodrigues.orchestrator.repository;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;

public interface RabbitRepository {

    void send(BillingEntity entity);
}
