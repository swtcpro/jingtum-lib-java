package com.blink.jtblc.client.bean;

import java.util.List;

public class AccountTx {
	private String account;// 钱包地址
	private String ledgerIndexMax;// 当前节点缓存的账本区间最大值
	private String ledgerIndexMin;// 当前节点缓存的账本区间最小值
	private Marker marker;
	private Integer limit;// 接口返回没有
	private List<Transactions> transactions;
	
	public Integer getLimit() {
		return limit;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	public List<Transactions> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getLedgerIndexMax() {
		return ledgerIndexMax;
	}
	
	public void setLedgerIndexMax(String ledgerIndexMax) {
		this.ledgerIndexMax = ledgerIndexMax;
	}
	
	public String getLedgerIndexMin() {
		return ledgerIndexMin;
	}
	
	public void setLedgerIndexMin(String ledgerIndexMin) {
		this.ledgerIndexMin = ledgerIndexMin;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}
}
