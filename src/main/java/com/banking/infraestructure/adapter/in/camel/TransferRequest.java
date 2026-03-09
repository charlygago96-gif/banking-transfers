package com.banking.infraestructure.adapter.in.camel;

import java.math.BigDecimal;

public class TransferRequest {

    private String sourceAccountId;
    private String targetAccountId;
    private BigDecimal amount;
    private String currency;

    public String getSourceAccountId() { return sourceAccountId; }
    public String getTargetAccountId() { return targetAccountId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }

    public void setSourceAccountId(String sourceAccountId) { this.sourceAccountId = sourceAccountId; }
    public void setTargetAccountId(String targetAccountId) { this.targetAccountId = targetAccountId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCurrency(String currency) { this.currency = currency; }
}