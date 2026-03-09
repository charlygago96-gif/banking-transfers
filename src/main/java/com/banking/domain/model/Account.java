package com.banking.domain.model;

import lombok.Getter;
import java.util.UUID;

// Entidad de dominio — representa una cuenta bancaria
@Getter
public class Account {

    private final String id;
    private final String owner;
    private Money balance;

    public Account(String id, String owner, Money balance) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
    }

    public static Account create(String owner, Money initialBalance) {
        return new Account(UUID.randomUUID().toString(), owner, initialBalance);
    }

    // Descuenta dinero de la cuenta
    public void debit(Money amount) {
        this.balance = this.balance.subtract(amount);
    }

    // Añade dinero a la cuenta
    public void credit(Money amount) {
        this.balance = this.balance.add(amount);
    }
}