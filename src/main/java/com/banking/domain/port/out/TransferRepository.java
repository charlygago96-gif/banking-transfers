package com.banking.domain.port.out;

import com.banking.domain.model.Transfer;

// Puerto de salida — define cómo se persisten las transferencias
public interface TransferRepository {
    Transfer save(Transfer transfer);
}