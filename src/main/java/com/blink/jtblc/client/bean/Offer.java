package com.blink.jtblc.client.bean;

public class Offer {
	private Integer flags;// 买卖类型(131072表示卖，否则是买)
	private String seq;// 余额
	private Boolean validated;//
	private AmountInfo takerGets;// 货币种类
	private AmountInfo takerPays;// 信任额度

	public Integer getFlags() {
		return flags;
	}

	public void setFlags(Integer flags) {
		this.flags = flags;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public AmountInfo getTakerGets() {
		return takerGets;
	}

	public void setTakerGets(AmountInfo takerGets) {
		this.takerGets = takerGets;
	}

	public AmountInfo getTakerPays() {
		return takerPays;
	}

	public void setTakerPays(AmountInfo takerPays) {
		this.takerPays = takerPays;
	}

}
