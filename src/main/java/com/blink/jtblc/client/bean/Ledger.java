package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 账本详情
 */
public class Ledger {
    private String ledgerHash;
    private String ledgerIndex;
    private boolean validated;
    private LedgerDetail ledgerDetail;

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

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public LedgerDetail getLedgerDetail() {
        return ledgerDetail;
    }

    public void setLedgerDetail(LedgerDetail ledgerDetail) {
        this.ledgerDetail = ledgerDetail;
    }
}
