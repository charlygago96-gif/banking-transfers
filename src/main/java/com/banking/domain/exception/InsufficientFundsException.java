package com.banking.domain.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String accountId) {
        super("Saldo insuficiente en la cuenta: " + accountId);
    }
}