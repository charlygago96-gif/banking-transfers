package com.banking.application.usecase;

import com.banking.domain.exception.AccountNotFoundException;
import com.banking.domain.model.Account;
import com.banking.domain.model.Money;
import com.banking.domain.model.Transfer;
import com.banking.domain.port.in.TransferUseCase;
import com.banking.domain.port.out.AccountRepository;
import com.banking.domain.port.out.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferUseCaseImpl implements TransferUseCase {

    private static final Logger log = LoggerFactory.getLogger(TransferUseCaseImpl.class);

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public TransferUseCaseImpl(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer execute(String sourceAccountId, String targetAccountId,
                            BigDecimal amount, String currency) {

        log.info("Iniciando transferencia de {} a {}", sourceAccountId, targetAccountId);

        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFoundException(sourceAccountId));

        Account target = accountRepository.findById(targetAccountId)
                .orElseThrow(() -> new AccountNotFoundException(targetAccountId));

        Money money = new Money(amount, currency);
        Transfer transfer = new Transfer(sourceAccountId, targetAccountId, money);

        try {
            source.debit(money);
            target.credit(money);
            accountRepository.save(source);
            accountRepository.save(target);
            transfer.complete();
            log.info("Transferencia {} completada", transfer.getId());
        } catch (Exception e) {
            transfer.fail();
            log.error("Transferencia {} fallida: {}", transfer.getId(), e.getMessage());
            throw e;
        }

        return transferRepository.save(transfer);
    }
}