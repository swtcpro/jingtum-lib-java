package com.blink.jtblc.client.bean;

/**
 * 账本详情
 */
public class Ledger {
    private String ledgerHash;//账本hash
    private String ledgerIndex;//账本高度/区块高度
    private Boolean validated;
    private LedgerDetail ledger;//账本详情

    public String getLedgerHash() {
        return ledgerHash;
    }

    public void setLedgerHash(String ledgerHash) {
        this.ledgerHash = ledgerHash;
    }

    public String getLedgerIndex() {
        return ledgerIndex;
    }

    public void setLedgerIndex(String ledgerIndex) {
        this.ledgerIndex = ledgerIndex;
    }

    public Boolean isValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Boolean getValidated() {
        return validated;
    }

    public LedgerDetail getLedger() {
        return ledger;
    }

    public void setLedger(LedgerDetail ledger) {
        this.ledger = ledger;
    }
}
