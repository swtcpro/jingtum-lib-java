package com.blink.jtblc.client.bean;

public class Marker {

	private Integer ledger;// 区块高度
	private Integer seq;// 条数

	public Integer getLedger() {
		return ledger;
	}

	public void setLedger(Integer ledger) {
		this.ledger = ledger;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
