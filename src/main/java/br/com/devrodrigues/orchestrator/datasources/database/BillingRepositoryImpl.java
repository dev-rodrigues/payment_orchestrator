package br.com.devrodrigues.orchestrator.datasources.database;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.datasources.database.repository.BillingEntityRepository;
import br.com.devrodrigues.orchestrator.repository.BillingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
    public List<BillingEntity> findByOrderId(String orderId) {
        return repository
                .findAll()
                .stream()
                .filter(
                        it -> it.getOrderId().equalsIgnoreCase(orderId)
                ).collect(toList());
    }
}
