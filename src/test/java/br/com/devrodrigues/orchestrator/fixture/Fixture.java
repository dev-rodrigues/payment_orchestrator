package br.com.devrodrigues.orchestrator.fixture;

import br.com.devrodrigues.orchestrator.core.BillingData;
import br.com.devrodrigues.orchestrator.core.PaymentRequest;
import br.com.devrodrigues.orchestrator.core.PaymentResponse;
import br.com.devrodrigues.orchestrator.core.objectsvalues.State;
import br.com.devrodrigues.orchestrator.core.build.BillingBuilder;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingEntity;
import br.com.devrodrigues.orchestrator.datasources.database.entity.BillingType;
import br.com.devrodrigues.orchestrator.openapi.model.Request;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;

import static br.com.devrodrigues.orchestrator.core.objectsvalues.PaymentType.SLIP;
import static br.com.devrodrigues.orchestrator.core.objectsvalues.State.PROCESSING;
import static java.math.BigDecimal.ONE;

public class Fixture {

    public static BillingEntity getBillingEntity() {
        return new BillingEntity(
                "userId",
                "orderId",
                State.PROCESSED,
                BillingType.SLIP,
                ONE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static BillingEntity getStartedBillingEntity() {
        var entity = getBillingEntity();
        entity.setState(State.CREATED);
        return entity;
    }

    public static BillingEntity getUpdatedBillingEntity() {
        var entity = getBillingEntity();
        entity.setState(PROCESSING);
        return entity;
    }

    public static PaymentRequest getValidPaymentRequest() {
        return new PaymentRequest(
                "orderId",
                SLIP,
                "value",
                ONE
        );
    }

    public static PaymentResponse getValidPaymentResponse() {
        return new PaymentResponse(
                1L,
                "dummy_orderId",
                "dummy_userId",
                ONE,
                PROCESSING,
                new BillingData(
                        "dummy_userId",
                        "dummy_fullName",
                        "dummy_document",
                        "dummy_address",
                        "dummy_phone"
                )
        );
    }

    public static Request getValidRequest() {
        return new Request()
                .orderId("orderId")
                .paymentType(Request.PaymentTypeEnum.SLIP)
                .userId("userId")
                .value(Float.MIN_VALUE);
    }
    
    public static Pair<BillingEntity, BillingBuilder> getOrchestratorResponse() {
        return Pair.of(
                getBillingEntity(),
                BillingBuilder.builder()
        );
    }

    public static String getValidJSONPaymentRequest() {
        return """
                {
                    "orderId": "orderId",
                    "paymentType": "SLIP",
                    "userId": "userId",
                    "value": 1.0
                }
                """;
    }

    public static String getValidJSONPaymentResponse() {
        return """
                {
                    "orderId": "orderId",
                    "paymentType": "SLIP",
                    "userId": "userId",
                    "value": 1.0,
                    "state": "PROCESSING",
                    "billingData": {
                        "userId": "userId",
                        "fullName": "fullName",
                        "document": "document",
                        "address": "address",
                        "phone": "phone"
                    }
                }
                """;
    }
}
