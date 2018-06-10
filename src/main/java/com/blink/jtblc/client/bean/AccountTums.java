package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 账号可接收和发送的货币
 */
public class AccountTums {
    private String ledgerHash;//账本hash
    private Integer ledgerIndex;//账本高度
    private List<String> receiveCurrencies;//可接收的货币列表
    private List<String> sendCurrencies;//可发送的货币列表
    private Boolean validated;//可发送的货币列表

	public Integer getLedgerIndex() {
		return ledgerIndex;
	}

	public void setLedgerIndex(Integer ledgerIndex) {
		this.ledgerIndex = ledgerIndex;
	}

	public String getLedgerHash() {
		return ledgerHash;
	}

	public void setLedgerHash(String ledgerHash) {
		this.ledgerHash = ledgerHash;
	}

	public List<String> getReceiveCurrencies() {
		return receiveCurrencies;
	}

	public void setReceiveCurrencies(List<String> receiveCurrencies) {
		this.receiveCurrencies = receiveCurrencies;
	}

	public List<String> getSendCurrencies() {
		return sendCurrencies;
	}

	public void setSendCurrencies(List<String> sendCurrencies) {
		this.sendCurrencies = sendCurrencies;
	}

	public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
}
