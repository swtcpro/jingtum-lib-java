package com.blink.jtblc.client.bean;

import java.util.List;

public class AccountTx {
	private String account;// 钱包地址
	private String ledgerIndexMax;// 当前节点缓存的账本区间最大值
	private String ledgerIndexMin;// 当前节点缓存的账本区间最小值
//	private Integer limit;// 接口返回没有
	private List<Transaction> transactions;
	
//	public Integer getLimit() {
//		return limit;
//	}
//	
//	public void setLimit(Integer limit) {
//		this.limit = limit;
//	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(List<Transaction> transactions) {
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
}
