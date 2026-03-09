package com.banking.domain.port.out;

import com.banking.domain.model.Account;
import java.util.Optional;

// Puerto de salida — define cómo se accede a las cuentas sin saber el detalle técnico
public interface AccountRepository {
    Optional<Account> findById(String id);
    Account save(Account account);
}