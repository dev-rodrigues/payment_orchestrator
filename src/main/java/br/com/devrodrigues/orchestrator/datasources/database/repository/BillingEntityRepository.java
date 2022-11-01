package br.com.devrodrigues.orchestrator.datasources.database.repository;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingEntityRepository extends JpaRepository<BillingEntity, String> {
}
