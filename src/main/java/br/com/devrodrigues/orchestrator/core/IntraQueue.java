package br.com.devrodrigues.orchestrator.core;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;

import java.io.Serializable;

public record IntraQueue(String exchangeName, String routingKey, BillingEntity messageData) implements Serializable {
}
