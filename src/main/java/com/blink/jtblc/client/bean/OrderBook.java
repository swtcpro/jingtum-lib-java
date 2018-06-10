package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 市场挂单列表
 */
public class OrderBook {
    private String ledgerCurrentIndex;//当前账本号
    private List<BookOffer> offers;//市场挂单列表

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
