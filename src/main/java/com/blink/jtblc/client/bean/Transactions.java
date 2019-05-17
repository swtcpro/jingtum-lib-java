package com.blink.jtblc.client.bean;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;

public class Transactions {

	private Integer date;// 时间戳
	private String hash;// 交易hash
	private String type;// 交易类型
	private String fee;// 手续费
	private String result;// 交易结果
	private List<Memo> memos;// 备注
	private String counterparty;// 交易对家
	private AmountInfo amount;// 交易金额对象
	private AmountInfo spent;//
	private String offertype;

	private AmountInfo gets;//
	private AmountInfo pays;//
	private Integer seq;//
	private Integer offerseq;//
	private String relationtype;
	private List params;
	private boolean isactive;
	private String method;
	private String payload;
	private String destination;
	private JSONArray effects;// 交易效果

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

	public List<Memo> getMemos() {
		return memos;
	}

	public void setMemos(List<Memo> memos) {
		this.memos = memos;
	}

	public String getCounterparty() {
		return counterparty;
	}

	public void setCounterparty(String counterparty) {
		this.counterparty = counterparty;
	}

	public AmountInfo getAmount() {
		return amount;
	}

	public void setAmount(AmountInfo amount) {
		this.amount = amount;
	}

	public JSONArray getEffects() {
		return effects;
	}

	public void setEffects(JSONArray effects) {
		this.effects = effects;
	}

	public AmountInfo getSpent() {
		return spent;
	}

	public void setSpent(AmountInfo spent) {
		this.spent = spent;
	}

	public String getOffertype() {
		return offertype;
	}

	public void setOffertype(String offertype) {
		this.offertype = offertype;
	}

	public AmountInfo getGets() {
		return gets;
	}

	public void setGets(AmountInfo gets) {
		this.gets = gets;
	}

	public AmountInfo getPays() {
		return pays;
	}

	public void setPays(AmountInfo pays) {
		this.pays = pays;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getOfferseq() {
		return offerseq;
	}

	public void setOfferseq(Integer offerseq) {
		this.offerseq = offerseq;
	}

	public String getRelationtype() {
		return relationtype;
	}

	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List getParams() {
		return params;
	}

	public void setParams(List params) {
		this.params = params;
	}

}
