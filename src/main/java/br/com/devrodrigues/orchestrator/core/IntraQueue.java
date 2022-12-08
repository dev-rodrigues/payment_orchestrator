package br.com.devrodrigues.orchestrator.core;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;

import java.io.Serializable;
import java.util.Objects;

public class IntraQueue implements Serializable {
    private String exchangeName;
    private String routingKey;
    private BillingEntity messageData;

    public IntraQueue() {
    }

    public IntraQueue(String exchangeName, String routingKey, BillingEntity messageData) {
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.messageData = messageData;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public BillingEntity getMessageData() {
        return messageData;
    }

    public void setMessageData(BillingEntity messageData) {
        this.messageData = messageData;
    }
}
