package br.com.devrodrigues.orchestrator.datasources.database.entity;

import br.com.devrodrigues.orchestrator.core.State;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;


@Entity(name = "billing")
public class BillingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    private String userId;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private BillingType type;

    private BigDecimal value;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public BillingEntity() {
    }

    public BillingEntity(String userId,
                         String orderId,
                         State state,
                         BillingType type,
                         BigDecimal value,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt) {
        this.userId = userId;
        this.orderId = orderId;
        this.state = state;
        this.type = type;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BillingType getType() {
        return type;
    }

    public void setType(BillingType type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BillingEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", state=" + state +
                ", type=" + type +
                ", value=" + value +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
