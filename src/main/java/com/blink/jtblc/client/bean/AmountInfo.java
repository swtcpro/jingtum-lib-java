package com.blink.jtblc.client.bean;

// 金额实体（支付金额，关系金额）
public class AmountInfo {
	// 货币数量
	private String value;
	// 货币种类，三到六个字母或20字节的自定义货币
	private String currency;
	// 货币发行方
	private String issuer;
	// 是否是基础token
	private Boolean isNative;

	public Boolean getIsNative() {
		return isNative;
	}

	public void setIsNative(Boolean isNative) {
		this.isNative = isNative;
	}

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
