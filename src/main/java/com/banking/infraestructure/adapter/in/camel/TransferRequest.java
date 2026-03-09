package com.banking.infraestructure.adapter.in.camel;

import lombok.Data;
import java.math.BigDecimal;

// Objeto de transferencia que llega por la ruta Camel
@Data
public class TransferRequest {
    private String sourceAccountId;
    private String targetAccountId;
    private BigDecimal amount;
    private String currency;
}