package com.blink.jtblc.client.bean;

import java.util.List;

public class Transaction {
    private Integer date;
    private String hash;
    private String transactionType;
    private String fee;
    private String transactionResult;
//    private List memos;//接口返回暂时没有
    private String counterparty;//接口返回暂时没有
    private List effects;
    private Amount amount;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }


    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public List getEffects() {
        return effects;
    }

    public void setEffects(List effects) {
        this.effects = effects;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionResult() {
        return transactionResult;
    }

    public void setTransactionResult(String transactionResult) {
        this.transactionResult = transactionResult;
    }
}
