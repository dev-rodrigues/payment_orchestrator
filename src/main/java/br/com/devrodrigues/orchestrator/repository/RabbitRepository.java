package br.com.devrodrigues.orchestrator.repository;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;

public interface RabbitRepository {
    void notify(BillingEntity saved);

    void sendToPay(BillingEntity saved);
}