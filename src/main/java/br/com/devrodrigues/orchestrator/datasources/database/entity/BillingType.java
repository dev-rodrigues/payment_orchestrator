package br.com.devrodrigues.orchestrator.datasources.database.entity;

import java.util.Objects;

public enum BillingType {
    SLIP,
    CREDIT_CARD;

    public static BillingType of(String name) {
        if (Objects.isNull(name)) {
            return null;
        }

        return BillingType.valueOf(name);
    }
}
