package com.blink.jtblc.client.bean;

/**
 * 账号信息
 */
public class AccountInfo {

    private AccountData accountData;
    private String ledgerCurrentIndex;
    private Integer ledgerIndex;
    private String validated;

    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

    public String getLedgerCurrentIndex() {
        return ledgerCurrentIndex;
    }

    public void setLedgerCurrentIndex(String ledgerCurrentIndex) {
        this.ledgerCurrentIndex = ledgerCurrentIndex;
    }

    public Integer getLedgerIndex() {
        return ledgerIndex;
    }

    public void setLedgerIndex(Integer ledgerIndex) {
        this.ledgerIndex = ledgerIndex;
    }

    public String getValidated() {
        return validated;
    }

    public void setValidated(String validated) {
        this.validated = validated;
    }
}

