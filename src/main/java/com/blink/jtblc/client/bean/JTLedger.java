package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 账本详情
 */
public class JTLedger {

    private Boolean accepted;
    private String accountHash;
    private Integer closeTime;
    private String closeTimeHuman;
    private Integer closeTimeResolution;
    private Boolean closed;
    private String hash;
    private String ledgerHash;
    private String ledgerIndex;
    private String parentHash;
    private String seqNum;
    private String totalCoins;
    private String transactionHash;
    private List<String> transactions;

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getAccountHash() {
        return accountHash;
    }

    public void setAccountHash(String accountHash) {
        this.accountHash = accountHash;
    }

    public Integer getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Integer closeTime) {
        this.closeTime = closeTime;
    }

    public String getCloseTimeHuman() {
        return closeTimeHuman;
    }

    public void setCloseTimeHuman(String closeTimeHuman) {
        this.closeTimeHuman = closeTimeHuman;
    }

    public Integer getCloseTimeResolution() {
        return closeTimeResolution;
    }

    public void setCloseTimeResolution(Integer closeTimeResolution) {
        this.closeTimeResolution = closeTimeResolution;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

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

    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(String totalCoins) {
        this.totalCoins = totalCoins;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
}
