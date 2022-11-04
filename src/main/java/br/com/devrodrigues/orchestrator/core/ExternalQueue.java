package br.com.devrodrigues.orchestrator.core;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;

public record ExternalQueue(
        String queueName,
        BillingEntity messageData
) {
}
