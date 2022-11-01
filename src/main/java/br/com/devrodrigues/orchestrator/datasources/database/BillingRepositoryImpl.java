package br.com.devrodrigues.orchestrator.datasources.database;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.datasources.database.repository.BillingEntityRepository;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import org.springframework.stereotype.Component;

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
}
