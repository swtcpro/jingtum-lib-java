package com.blink.jtblc.client.bean;

import java.util.List;

public class OrderBook {
    private String ledgerCurrentIndex;
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
