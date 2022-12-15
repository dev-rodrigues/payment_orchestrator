package br.com.devrodrigues.orchestrator.repository;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;

import java.util.List;
import java.util.UUID;

public interface BillingRepository {
    BillingEntity store(BillingEntity entity);

//    List<BillingEntity> findByOrderId(String orderId);

    BillingEntity findById(Long billingId) throws Exception;

    List<BillingEntity> findAll();
}
