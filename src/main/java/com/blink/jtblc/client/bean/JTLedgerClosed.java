package com.blink.jtblc.client.bean;

/**
 * 账本信息
 */
public class JTLedgerClosed {
    private String ledgerHash;
    private String ledgerIndex;

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
}
