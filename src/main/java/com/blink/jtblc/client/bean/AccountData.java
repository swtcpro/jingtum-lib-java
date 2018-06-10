package com.blink.jtblc.client.bean;

/**
 * 账本信息
 */
public class AccountData {
    private String account;//钱包地址
    private String balance;//swt数量
//    private String domain;//
    private Integer flags;//属性标志
//    private String messageKey;//公共密钥，用于发送加密的邮件到这个帐户
    private Integer ownerCount;//用户拥有的挂单数和信任线数量的总和
    private String previousTxnId;//操作该帐号的上一笔交易hash
    private Integer previousTxnLgrSeq;//该帐号上一笔交易所在的账本号
//    private String regularKey;
    private Integer sequence;//账号当前序列号
//    private Integer transferRate;
    private String index;//该数据所在索引hash

    private String ledgerEntryType;//

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

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
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

    public Integer getPreviousTxnLgrSeq() {
        return previousTxnLgrSeq;
    }

    public void setPreviousTxnLgrSeq(Integer previousTxnLgrSeq) {
        this.previousTxnLgrSeq = previousTxnLgrSeq;
    }


    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLedgerEntryType() {
        return ledgerEntryType;
    }

    public void setLedgerEntryType(String ledgerEntryType) {
        this.ledgerEntryType = ledgerEntryType;
    }
}
