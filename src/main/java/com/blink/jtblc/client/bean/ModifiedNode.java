package com.blink.jtblc.client.bean;

public class ModifiedNode {
	private FinalFields finalFields;
	private String ledgerEntryType;
	private String ledgerIndex;
	private PreviousFields previousFields;
	private String previousTxnID;
	private Integer previousTxnLgrSeq;
	
	public String getLedgerEntryType() {
		return ledgerEntryType;
	}
	
	public void setLedgerEntryType(String ledgerEntryType) {
		this.ledgerEntryType = ledgerEntryType;
	}
	
	public String getLedgerIndex() {
		return ledgerIndex;
	}
	
	public void setLedgerIndex(String ledgerIndex) {
		this.ledgerIndex = ledgerIndex;
	}
	
	public PreviousFields getPreviousFields() {
		return previousFields;
	}
	
	public void setPreviousFields(PreviousFields previousFields) {
		this.previousFields = previousFields;
	}
	
	public String getPreviousTxnID() {
		return previousTxnID;
	}
	
	public void setPreviousTxnID(String previousTxnID) {
		this.previousTxnID = previousTxnID;
	}
	
	public Integer getPreviousTxnLgrSeq() {
		return previousTxnLgrSeq;
	}
	
	public void setPreviousTxnLgrSeq(Integer previousTxnLgrSeq) {
		this.previousTxnLgrSeq = previousTxnLgrSeq;
	}
	
	public FinalFields getFinalFields() {
		return finalFields;
	}
	
	public void setFinalFields(FinalFields finalFields) {
		this.finalFields = finalFields;
	}
}
