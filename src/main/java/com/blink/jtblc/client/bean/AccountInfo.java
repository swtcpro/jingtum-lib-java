package com.blink.jtblc.client.bean;

/**
 * 账号信息
 */
public class AccountInfo {

    private AccountData accountData;//账号信息
    private String ledgerHash;//账本hash
    private Integer ledgerIndex;//账本高度
//    private String validated;//
    private Boolean validated;//交易是否通过验证

    public AccountData getAccountData() {
        return accountData;
    }

    public void setAccountData(AccountData accountData) {
        this.accountData = accountData;
    }

    public String getLedgerHash() {
        return ledgerHash;
    }

    public void setLedgerHash(String ledgerHash) {
        this.ledgerHash = ledgerHash;
    }

    public Integer getLedgerIndex() {
        return ledgerIndex;
    }

    public void setLedgerIndex(Integer ledgerIndex) {
        this.ledgerIndex = ledgerIndex;
    }

    public Boolean isValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
}

