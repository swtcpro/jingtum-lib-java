package com.blink.jtblc.client.bean;

import java.util.List;

public class AccountTx{
    private String account;
    private String ledgerIndexMax;
    private String ledgerIndexMin;
//    private String marker;//接口返回没有
    private List<Transaction> transactions;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLedgerIndexMax() {
        return ledgerIndexMax;
    }

    public void setLedgerIndexMax(String ledgerIndexMax) {
        this.ledgerIndexMax = ledgerIndexMax;
    }

    public String getLedgerIndexMin() {
        return ledgerIndexMin;
    }

    public void setLedgerIndexMin(String ledgerIndexMin) {
        this.ledgerIndexMin = ledgerIndexMin;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
