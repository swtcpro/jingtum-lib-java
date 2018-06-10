package com.blink.jtblc.client.bean;


public class Transaction {
    private String account;//
    private String amount;//交易金额对象
    private String destination;//
    private String fee;//手续费
    private Long flags;//
    private Integer sequence;//
    private String signingPubKey;//
    private Integer timestamp;//
    private String transactionType;//交易类型
    private String txnSignature;//
    private Integer date;//时间戳
    private String hash;//交易hash
    private Integer inLedger;//
    private Integer ledgerIndex;//

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Long getFlags() {
        return flags;
    }

    public void setFlags(Long flags) {
        this.flags = flags;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getSigningPubKey() {
        return signingPubKey;
    }

    public void setSigningPubKey(String signingPubKey) {
        this.signingPubKey = signingPubKey;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTxnSignature() {
        return txnSignature;
    }

    public void setTxnSignature(String txnSignature) {
        this.txnSignature = txnSignature;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getInLedger() {
        return inLedger;
    }

    public void setInLedger(Integer inLedger) {
        this.inLedger = inLedger;
    }

    public Integer getLedgerIndex() {
        return ledgerIndex;
    }

    public void setLedgerIndex(Integer ledgerIndex) {
        this.ledgerIndex = ledgerIndex;
    }
}
