package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 账号挂单
 */
public class AccountOffers {

    private String account;
    private String ledgerHash;
    private Integer ledgerIndex;
    private List<Offer> offers;
    
    private Integer ledgerCurrentIndex;

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

	public Integer getLedgerCurrentIndex() {
		return ledgerCurrentIndex;
	}

	public void setLedgerCurrentIndex(Integer ledgerCurrentIndex) {
		this.ledgerCurrentIndex = ledgerCurrentIndex;
	}
}

