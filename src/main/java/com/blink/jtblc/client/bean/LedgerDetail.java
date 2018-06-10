package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 账本详情
 */
public class LedgerDetail {
    private Boolean accepted;//区块是否已经产生
    private String accountHash;//状态hash树根
    private Integer closeTime;//关闭时间
    private String closeTimeHuman;//关闭时间
    private Integer closeTimeResolution;//关闭周期
    private Boolean closed;//账本是否已经关闭
    private String hash;//账本hash
    private String parentHash;//上一区块hash值
    private String seqNum;//账本高度/区块高度
    private String totalCoins;//swt总量
    private String transactionHash;//交易hash树根
    private List<String> transactions;//该账本里的交易列表

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
