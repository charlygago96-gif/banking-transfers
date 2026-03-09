package com.banking.infraestructure.adapter.out.persistence;

import com.banking.domain.model.Account;
import com.banking.domain.model.Money;
import com.banking.domain.port.out.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountRepositoryAdapter implements AccountRepository {

    private final AccountJpaRepository jpaRepository;

    public AccountRepositoryAdapter(AccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Account> findById(String id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = toEntity(account);
        jpaRepository.save(entity);
        return account;
    }

    private Account toDomain(AccountEntity e) {
        return new Account(e.getId(), e.getOwner(),
                new Money(e.getBalance(), e.getCurrency()));
    }

    private AccountEntity toEntity(Account a) {
        return new AccountEntity(
                a.getId(), a.getOwner(),
                a.getBalance().amount(), a.getBalance().currency());
    }
}