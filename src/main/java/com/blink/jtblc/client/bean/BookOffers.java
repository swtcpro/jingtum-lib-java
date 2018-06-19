package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 市场挂单列表
 */
public class BookOffers {
	private String ledgerCurrentIndex;// 账号地址
	private List<BookOffer> offers;
	
	public String getLedgerCurrentIndex() {
		return ledgerCurrentIndex;
	}
	
	public void setLedgerCurrentIndex(String ledgerCurrentIndex) {
		this.ledgerCurrentIndex = ledgerCurrentIndex;
	}
	
	public List<BookOffer> getOffers() {
		return offers;
	}
	
	public void setOffers(List<BookOffer> offers) {
		this.offers = offers;
	}
}
