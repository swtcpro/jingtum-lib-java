package com.blink.jtblc.client.bean;

import java.util.List;
import java.util.Map;

public class Transaction {
	 private String account;//
//   private String destination;//
//   
//   private Long flags;//
//   private Integer sequence;//
//   private String signingPubKey;//
   private String transactionType;//交易类型
//   private String txnSignature;//
   private Integer date;//时间戳
   private String hash;//交易hash
   private String type;//交易类型
   private String fee;//手续费
   private String result;//交易结果
   private Map<String,String> memos;//备注
   private String counterparty;//交易对家
   private String amount; //交易金额对象
   private List effects;//交易效果
   private String spent;
   private String offertype;
   private String seq;
   private String gets;
   private String pays;
   private boolean isactive;
   private List params;
   private String method;
   private String payload;
   private String offerseq;
   
	public String getOfferseq() {
	return offerseq;
}
public void setOfferseq(String offerseq) {
	this.offerseq = offerseq;
}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Map<String,String> getMemos() {
		return memos;
	}
	public void setMemos(Map<String,String> memos) {
		this.memos = memos;
	}
	public String getCounterparty() {
		return counterparty;
	}
	public void setCounterparty(String counterparty) {
		this.counterparty = counterparty;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public List getEffects() {
		return effects;
	}
	public void setEffects(List effects) {
		this.effects = effects;
	}
	public String getSpent() {
		return spent;
	}
	public void setSpent(String spent) {
		this.spent = spent;
	}
	public String getOffertype() {
		return offertype;
	}
	public void setOffertype(String offertype) {
		this.offertype = offertype;
	}
	public String getGets() {
		return gets;
	}
	public void setGets(String gets) {
		this.gets = gets;
	}
	
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public boolean isIsactive() {
		return isactive;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	public List getParams() {
		return params;
	}
	public void setParams(List params) {
		this.params = params;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	

}
