package br.com.devrodrigues.orchestrator.core;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;

import java.util.Objects;

public final class ExternalQueue {
    private final String queueName;
    private final BillingEntity messageData;

    public ExternalQueue(
            String queueName,
            BillingEntity messageData
    ) {
        this.queueName = queueName;
        this.messageData = messageData;
    }

    public String queueName() {
        return queueName;
    }

    public BillingEntity messageData() {
        return messageData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ExternalQueue) obj;
        return Objects.equals(this.queueName, that.queueName) &&
                Objects.equals(this.messageData, that.messageData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queueName, messageData);
    }

    @Override
    public String toString() {
        return "ExternalQueue[" +
                "queueName=" + queueName + ", " +
                "messageData=" + messageData + ']';
    }

}
