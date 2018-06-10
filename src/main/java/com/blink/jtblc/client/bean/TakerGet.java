package com.blink.jtblc.client.bean;

/**
 * 货币种类
 */
public class TakerGet {
    private String value;//金额
    private String currency;//货币种类
    private String issuer;//货币

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
