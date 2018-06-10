package com.blink.jtblc.client.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Meta {
    private List<AffectedNode> affectednodes = new ArrayList<>();
    private Integer transactionIndex;
    private String transactionResult;

    public List<AffectedNode> getAffectednodes() {
        return affectednodes;
    }

    public void setAffectednodes(List<AffectedNode> affectednodes) {
        this.affectednodes = affectednodes;
    }

    public Integer getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(Integer transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public String getTransactionResult() {
        return transactionResult;
    }

    public void setTransactionResult(String transactionResult) {
        this.transactionResult = transactionResult;
    }
}
