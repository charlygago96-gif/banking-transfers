package com.banking.domain.port.in;

import com.banking.domain.model.Transfer;
import java.math.BigDecimal;

// Puerto de entrada — define qué puede hacer el sistema con transferencias
public interface TransferUseCase {
    Transfer execute(String sourceAccountId, String targetAccountId, BigDecimal amount, String currency);
}