package com.banking.infraestructure.adapter.out.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
public class TransferEntity {

    @Id
    private String id;
    private String sourceAccountId;
    private String targetAccountId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime createdAt;

    public TransferEntity() {}

    public TransferEntity(String id, String sourceAccountId, String targetAccountId,
                          BigDecimal amount, String currency, String status, LocalDateTime createdAt) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getSourceAccountId() { return sourceAccountId; }
    public String getTargetAccountId() { return targetAccountId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}