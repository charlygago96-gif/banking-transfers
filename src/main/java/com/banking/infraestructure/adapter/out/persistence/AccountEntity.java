package com.banking.infraestructure.adapter.out.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private String id;
    private String owner;
    private BigDecimal balance;
    private String currency;

    public AccountEntity() {}

    public AccountEntity(String id, String owner, BigDecimal balance, String currency) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
        this.currency = currency;
    }

    public String getId() { return id; }
    public String getOwner() { return owner; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
}