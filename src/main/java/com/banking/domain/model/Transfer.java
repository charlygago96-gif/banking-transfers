package com.banking.domain.model;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

// Entidad de dominio — representa una transferencia entre cuentas
@Getter
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

    public void complete() {
        this.status = TransferStatus.COMPLETADA;
    }

    public void fail() {
        this.status = TransferStatus.FALLIDA;
    }
}