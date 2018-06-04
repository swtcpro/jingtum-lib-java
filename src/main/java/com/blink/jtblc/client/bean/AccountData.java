package com.blink.jtblc.client.bean;

public class AccountData {
    private String account;
    private String balance;
    private String domain;
    private Integer flags;
    private String messageKey;
    private Integer ownerCount;
    private String previousTxnId;
    private Integer eviousTxnLgrSeq;
    private String regularKey;
    private Integer sequence;
    private Integer transferRate;
    private String index;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public Integer getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(Integer ownerCount) {
        this.ownerCount = ownerCount;
    }

    public String getPreviousTxnId() {
        return previousTxnId;
    }

    public void setPreviousTxnId(String previousTxnId) {
        this.previousTxnId = previousTxnId;
    }

    public Integer getEviousTxnLgrSeq() {
        return eviousTxnLgrSeq;
    }

    public void setEviousTxnLgrSeq(Integer eviousTxnLgrSeq) {
        this.eviousTxnLgrSeq = eviousTxnLgrSeq;
    }

    public String getRegularKey() {
        return regularKey;
    }

    public void setRegularKey(String regularKey) {
        this.regularKey = regularKey;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(Integer transferRate) {
        this.transferRate = transferRate;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
