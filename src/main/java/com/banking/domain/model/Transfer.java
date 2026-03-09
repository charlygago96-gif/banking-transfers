package com.banking.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transfer {

    private final String id;
    private final String sourceAccountId;
    private final String targetAccountId;
    private final Money amount;
    private final LocalDateTime createdAt;
    private TransferStatus status;

    public Transfer(String sourceAccountId, String targetAccountId, Money amount) {
        this.id = UUID.randomUUID().toString();
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.status = TransferStatus.PENDIENTE;
    }

    public String getId() { return id; }
    public String getSourceAccountId() { return sourceAccountId; }
    public String getTargetAccountId() { return targetAccountId; }
    public Money getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public TransferStatus getStatus() { return status; }

    public void complete() { this.status = TransferStatus.COMPLETADA; }
    public void fail() { this.status = TransferStatus.FALLIDA; }
}