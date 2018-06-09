package com.blink.jtblc.client.bean;

public class BookOffer {
    private Integer account;

    private String ookDirector;
    private String bookNode;

    private Integer flags;

    private String dgerEntryTy;
    private String ownerNode;

    private String previousTxnId;
    private Integer previousTxnLgrSeq;
    private Integer sequence;
    private boolean validated;

    private TakerGet takerGets;
    private BookTakerPay takerPays;

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getOokDirector() {
        return ookDirector;
    }

    public void setOokDirector(String ookDirector) {
        this.ookDirector = ookDirector;
    }

    public String getBookNode() {
        return bookNode;
    }

    public void setBookNode(String bookNode) {
        this.bookNode = bookNode;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public String getDgerEntryTy() {
        return dgerEntryTy;
    }

    public void setDgerEntryTy(String dgerEntryTy) {
        this.dgerEntryTy = dgerEntryTy;
    }

    public String getOwnerNode() {
        return ownerNode;
    }

    public void setOwnerNode(String ownerNode) {
        this.ownerNode = ownerNode;
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

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public TakerGet getTakerGets() {
        return takerGets;
    }

    public void setTakerGets(TakerGet takerGets) {
        this.takerGets = takerGets;
    }

    public BookTakerPay getTakerPays() {
        return takerPays;
    }

    public void setTakerPays(BookTakerPay takerPays) {
        this.takerPays = takerPays;
    }
}
