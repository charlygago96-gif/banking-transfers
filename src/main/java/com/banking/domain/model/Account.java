package com.banking.domain.model;

import java.util.UUID;

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

    public String getId() { return id; }
    public String getOwner() { return owner; }
    public Money getBalance() { return balance; }

    public void debit(Money amount) {
        this.balance = this.balance.subtract(amount);
    }

    public void credit(Money amount) {
        this.balance = this.balance.add(amount);
    }
}