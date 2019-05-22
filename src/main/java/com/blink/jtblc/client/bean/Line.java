package com.blink.jtblc.client.bean;

/**
 * 该账户的信任线
 */
public class Line {
    private String account;//信任的银关
    private String issuer;//发行人
    private String balance;//余额
    private String currency;//货币种类
    private String limit;//信任额度
    private String limitPeer;//对方设置的信任额度，默认0
    private Integer qualityIn;//兑换比例，默认0，暂时未用
    private Integer qualityOut;//兑换比例，默认0，暂时未用

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLimitPeer() {
        return limitPeer;
    }

    public void setLimitPeer(String limitPeer) {
        this.limitPeer = limitPeer;
    }

    public Integer getQualityIn() {
        return qualityIn;
    }

    public void setQualityIn(Integer qualityIn) {
        this.qualityIn = qualityIn;
    }

    public Integer getQualityOut() {
        return qualityOut;
    }

    public void setQualityOut(Integer qualityOut) {
        this.qualityOut = qualityOut;
    }

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
    
}
