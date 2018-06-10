package com.blink.jtblc.client.bean;



/**
 * 交易信息
 */
public class Account {
    private String account;//钱包地址
    private String amount;//交易金额
    private String destination;//交易对家地址
    private String fee;//交易费
    private Long flags;//交易标记
//    private List<String> memos; 返回值中没有，所以注释
    private Integer sequence;//自身账号的交易号
    private String signingPubKey;//签名公钥
    private Integer timestamp;//交易提交时间戳
    private String transactionType;//交易类型
    private String txnSignature;//交易签名
    private Integer date;//交易进账本时间
    private String hash;//交易hash
    private Integer inLedger;//交易所在的账本号
    private String ledgerIndex;//账本高度
    private Meta meta;//交易影响的节点
    private Boolean validated;//交易是否通过验证


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

    public String getLedgerIndex() {
        return ledgerIndex;
    }

    public void setLedgerIndex(String ledgerIndex) {
        this.ledgerIndex = ledgerIndex;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }
}


