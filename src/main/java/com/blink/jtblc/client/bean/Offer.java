package com.blink.jtblc.client.bean;

public class Offer {
    private Integer flags;//买卖类型(131072表示卖，否则是买)
    private String seq;//余额
    private Boolean validated;//
    private TakerGet takerGets;//货币种类
    private TakerPay takerPays;//信任额度

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
