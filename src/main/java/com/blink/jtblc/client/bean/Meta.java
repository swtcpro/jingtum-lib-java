package com.blink.jtblc.client.bean;

import java.util.List;

public class Meta {
    private List<String> AffectedNodes;
    private Integer TransactionInde;
    private Integer ransactionResul;

    public List<String> getAffectedNodes() {
        return AffectedNodes;
    }

    public void setAffectedNodes(List<String> affectedNodes) {
        AffectedNodes = affectedNodes;
    }

    public Integer getTransactionInde() {
        return TransactionInde;
    }

    public void setTransactionInde(Integer transactionInde) {
        TransactionInde = transactionInde;
    }

    public Integer getRansactionResul() {
        return ransactionResul;
    }

    public void setRansactionResul(Integer ransactionResul) {
        this.ransactionResul = ransactionResul;
    }
}
