package br.com.devrodrigues.orchestrator.datasources.message;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.repository.RabbitRepository;
import org.springframework.stereotype.Component;

@Component
public class RabbitRepositoryImpl implements RabbitRepository {
    @Override
    public void notify(BillingEntity saved) {

    }

    @Override
    public void sendToPay(BillingEntity saved) {

    }
}