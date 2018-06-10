package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 账号关系
 */
public class AccountRelations {
    private String account;//钱包地址
    private String ledgerHash;//账本hash
    private Integer ledgerIndex;//账本高度
    private Boolean validated;//交易是否通过验证
    private List<Line> lines;//该账户的信任线

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

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
