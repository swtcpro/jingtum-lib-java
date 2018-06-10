package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 账号挂单
 */
public class AccountOffers {

    private String account;//钱包地址
    private String ledgerHash;//账本hash
    private Integer ledgerIndex;//账本高度
    private List<Offer> offers;//该账户的挂单列表

    private Boolean validated;//交易是否通过验证

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Boolean isValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
}

