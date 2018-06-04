package com.blink.jtblc.client.bean;

/**
 * 账号信息
 */
public class JTAccountInfo {

    private AccountData accountData;
    private String ledgerHash;
    private Integer ledgerCurrentIndex;
    private Boolean validated;

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
    
    public Integer getLedgerCurrentIndex() {
		return ledgerCurrentIndex;
	}

	public void setLedgerCurrentIndex(Integer ledgerCurrentIndex) {
		this.ledgerCurrentIndex = ledgerCurrentIndex;
	}

	public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
}

