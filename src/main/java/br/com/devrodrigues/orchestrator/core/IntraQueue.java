package br.com.devrodrigues.orchestrator.core;

public record IntraQueue(String exchangeName, String routingKey, String messageData) {
}
