package com.blink.jtblc.client.bean;

import java.util.List;

/*
 * 交易内容
 */
public class TxJson {
	// 账号地址
	private String account;
	// 交易金额
	private String amount;
	// 对家
	private String destination;
	// 交易费
	private String fee;
	// 交易标记
	private Long Flags;
	// 备注
	private List<Object> memos;
	// 单子序列号
	private Integer sequence;
	// 签名公钥
	private String signingPubKey;
	// 交易类型:TrustSet信任;RelationDel解冻；RelationSet 授权/冻结
	private String transactionType;
	// 交易签名
	private String txnSignature;
	// 交易hash
	private String hash;
	// 关系的额度
	private Account limitAmount;
	// 关系类型：0信任；1授权；3冻结/解冻
	private Integer relationType;
	// 关系对家
	private String target;
	// 时间戳
	private Integer timestamp;
	// 取消的单子号
	private Integer offerSequence;
	// 对家得到的Object
	private AmountInfo takerGets;
	// 对家支付的Object
	private String takerPays;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	public Long getFlags() {
		return Flags;
	}
	
	public void setFlags(Long flags) {
		Flags = flags;
	}
	
	public List<Object> getMemos() {
		return memos;
	}
	
	public void setMemos(List<Object> memos) {
		this.memos = memos;
	}
	
	public Integer getSequence() {
		return sequence;
	}
	
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public String getSigningPubKey() {
		return signingPubKey;
	}
	
	public void setSigningPubKey(String signingPubKey) {
		this.signingPubKey = signingPubKey;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getTxnSignature() {
		return txnSignature;
	}
	
	public void setTxnSignature(String txnSignature) {
		this.txnSignature = txnSignature;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public Account getLimitAmount() {
		return limitAmount;
	}
	
	public void setLimitAmount(Account limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	public Integer getRelationType() {
		return relationType;
	}
	
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public Integer getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}
	
	public Integer getOfferSequence() {
		return offerSequence;
	}
	
	public void setOfferSequence(Integer offerSequence) {
		this.offerSequence = offerSequence;
	}
	
	public AmountInfo getTakerGets() {
		return takerGets;
	}
	
	public void setTakerGets(AmountInfo takerGets) {
		this.takerGets = takerGets;
	}

	public String getTakerPays() {
		return takerPays;
	}

	public void setTakerPays(String takerPays) {
		this.takerPays = takerPays;
	}
	
}
