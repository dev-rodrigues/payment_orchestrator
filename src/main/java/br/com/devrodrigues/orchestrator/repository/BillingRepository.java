package br.com.devrodrigues.orchestrator.repository;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;

import java.util.List;

public interface BillingRepository {
    BillingEntity store(BillingEntity entity);
    BillingEntity findById(Long billingId) throws Exception;
    List<BillingEntity> findAll();
}
