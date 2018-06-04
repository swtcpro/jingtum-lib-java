package com.blink.jtblc.client.bean;

public class Offer {
    private Integer flags;
    private String seq;
    private Boolean validated;
    private TakerGet takerGets;
    private TakerPay takerPays;

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

    public TakerGet getTakerGets() {
        return takerGets;
    }

    public void setTakerGets(TakerGet takerGets) {
        this.takerGets = takerGets;
    }

	public TakerPay getTakerPays() {
		return takerPays;
	}

	public void setTakerPays(TakerPay takerPays) {
		this.takerPays = takerPays;
	}
}
