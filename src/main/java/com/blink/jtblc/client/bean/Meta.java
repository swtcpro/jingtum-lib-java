package com.blink.jtblc.client.bean;

import java.util.ArrayList;
import java.util.List;

public class Meta {
	private List<AffectedNode> affectedNodes = new ArrayList<AffectedNode>();
	private Integer transactionIndex;
	private String transactionResult;
	
	public List<AffectedNode> getAffectedNodes() {
		return affectedNodes;
	}
	
	public void setAffectedNodes(List<AffectedNode> affectedNodes) {
		this.affectedNodes = affectedNodes;
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
