package com.blink.jtblc.client.bean;


import java.util.List;

public class AccountTx{
    private String account;//钱包地址
    private String ledgerIndexMax;//当前节点缓存的账本区间最大值
    private String ledgerIndexMin;//当前节点缓存的账本区间最小值
//    private String marker;//接口返回没有
    private List<Transaction> tx;



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

    public List<Transaction> getTx() {
        return tx;
    }

    public void setTx(List<Transaction> tx) {
        this.tx = tx;
    }
}
