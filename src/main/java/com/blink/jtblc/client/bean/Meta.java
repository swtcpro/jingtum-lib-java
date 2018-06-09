package com.blink.jtblc.client.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Meta {
    private List<Map> affectednodes = new ArrayList<>();
    private Integer TransactionInde;
    private Integer ransactionResult;

    public List<Map> getAffectednodes() {
        return affectednodes;
    }

    public void setAffectednodes(List<Map> affectednodes) {
        this.affectednodes = affectednodes;
    }

    public Integer getTransactionInde() {
        return TransactionInde;
    }

    public void setTransactionInde(Integer transactionInde) {
        TransactionInde = transactionInde;
    }

    public Integer getRansactionResult() {
        return ransactionResult;
    }

    public void setRansactionResult(Integer ransactionResult) {
        this.ransactionResult = ransactionResult;
    }
}
