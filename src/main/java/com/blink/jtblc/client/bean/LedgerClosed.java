package com.blink.jtblc.client.bean;

/**
 * 账本信息
 */
public class LedgerClosed {
    private String ledgerHash;//账本hash
    private String ledgerIndex;//账本高度/区块高度

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
