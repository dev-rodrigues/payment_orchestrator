package br.com.devrodrigues.orchestrator.datasources.database;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.datasources.database.repository.BillingEntityRepository;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillingRepositoryImpl implements BillingRepository {

    private final BillingEntityRepository repository;

    public BillingRepositoryImpl(BillingEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public BillingEntity store(BillingEntity entity) {
        return repository.save(entity);
    }

    @Override
    public BillingEntity findById(Long billingId) {
        return repository.findById(billingId).orElseThrow();
    }

    @Override
    public List<BillingEntity> findAll() {
        return repository.findAll();
    }
}
