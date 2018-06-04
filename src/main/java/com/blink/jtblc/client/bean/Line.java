package com.blink.jtblc.client.bean;

public class Line {
    private String account;
    private String balance;
    private String currency;
    private String limit;
    private String limitPeer;
    private Integer qualityIn;
    private Integer qualityOut;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLimitPeer() {
        return limitPeer;
    }

    public void setLimitPeer(String limitPeer) {
        this.limitPeer = limitPeer;
    }

    public Integer getQualityIn() {
        return qualityIn;
    }

    public void setQualityIn(Integer qualityIn) {
        this.qualityIn = qualityIn;
    }

    public Integer getQualityOut() {
        return qualityOut;
    }

    public void setQualityOut(Integer qualityOut) {
        this.qualityOut = qualityOut;
    }
}
