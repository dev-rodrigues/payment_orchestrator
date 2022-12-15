package br.com.devrodrigues.orchestrator.datasources.database;

import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static br.com.devrodrigues.orchestrator.core.objectsvalues.State.PROCESSED;
import static br.com.devrodrigues.orchestrator.fixture.Fixture.getBillingEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(BillingRepositoryImpl.class)
@DataJpaTest
class BillingRepositoryImplTest {

    @Autowired
    BillingRepositoryImpl repository;

    BillingEntity entity;

    @BeforeEach
    void setUp() {
        entity = repository.store(getBillingEntity());
    }

    @Test
    void should_save_billing() {
        // given: a valid billing entity
        var billingEntity = getBillingEntity();

        // when: save billing
        var response = repository.store(billingEntity);

        // then: billing saved
        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void should_updated_billing() {
        // given: a valid billing entity
        var billingEntity = entity;

        // when: update billing
        billingEntity.setState(PROCESSED);

        // then: billing updated
        var response = repository.store(billingEntity);


        // then: billing updated
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(PROCESSED, response.getState());
        assertEquals(billingEntity.getId(), response.getId());
    }

    @Test
    void should_find_billing_by_id() {
        // given: a valid billing entity
        var billingEntity = entity;

        // when: find billing
        var response = repository.findById(billingEntity.getId());

        // then: billing found
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(billingEntity.getId(), response.getId());
    }

    @Test
    void should_find_by_order_id() {
        // given: a valid billing entity
        var billingEntity = entity;

        // when: find billing
        var response = repository.findById(billingEntity.getId());

        // then: billing found
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(billingEntity.getId(), response.getId());
    }

    @Test
    void should_return_all_billings() {
        // when: find billing
        var response = repository.findAll();

        // then: billing found
        assertNotNull(response);
        assertEquals(1, response.size());
    }
}