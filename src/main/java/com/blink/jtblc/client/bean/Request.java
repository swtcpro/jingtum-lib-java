package com.blink.jtblc.client.bean;

/**
 * 请求信息
 */
public class Request {
    private Integer feeBase;
    private Integer feeRef;
    private String hostId;
    private String ledgerHash;
    private Integer ledgerIndex;
    private Integer ledgerTime;
    private String pubkeyNode;
    private Integer reserveBase;
    private Integer reserveInc;
    private String serverStatus;
    private String validatedLedgers;

    public Integer getFeeBase() {
        return feeBase;
    }

    public void setFeeBase(Integer feeBase) {
        this.feeBase = feeBase;
    }

    public Integer getFeeRef() {
        return feeRef;
    }

    public void setFeeRef(Integer feeRef) {
        this.feeRef = feeRef;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
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

    public Integer getLedgerTime() {
        return ledgerTime;
    }

    public void setLedgerTime(Integer ledgerTime) {
        this.ledgerTime = ledgerTime;
    }

    public String getPubkeyNode() {
        return pubkeyNode;
    }

    public void setPubkeyNode(String pubkeyNode) {
        this.pubkeyNode = pubkeyNode;
    }


    public Integer getReserveInc() {
        return reserveInc;
    }

    public void setReserveInc(Integer reserveInc) {
        this.reserveInc = reserveInc;
    }

    public Integer getReserveBase() {
        return reserveBase;
    }

    public void setReserveBase(Integer reserveBase) {
        this.reserveBase = reserveBase;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public String getValidatedLedgers() {
        return validatedLedgers;
    }

    public void setValidatedLedgers(String validatedLedgers) {
        this.validatedLedgers = validatedLedgers;
    }
}
