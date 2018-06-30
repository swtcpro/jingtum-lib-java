package com.blink.jtblc.client.bean;

/**
 * 返回的支付信息对象
 * 描述：buildPaymentTx返回对象
 */
public class TransactionInfo {
	
	// 请求结果 
	private String engineResult;
	// 请求结果编码
	private String engineResultCode;
	// 请求结果message信息 
	private String engineResultMessage;
	// 16进制签名后的交易 
	private String txBlob;
	// 交易内容
	private TxJson txJson;
	
	public String getEngineResult() {
		return engineResult;
	}
	public void setEngineResult(String engineResult) {
		this.engineResult = engineResult;
	}
	public String getEngineResultCode() {
		return engineResultCode;
	}
	public void setEngineResultCode(String engineResultCode) {
		this.engineResultCode = engineResultCode;
	}
	public String getEngineResultMessage() {
		return engineResultMessage;
	}
	public void setEngineResultMessage(String engineResultMessage) {
		this.engineResultMessage = engineResultMessage;
	}
	public String getTxBlob() {
		return txBlob;
	}
	public void setTxBlob(String txBlob) {
		this.txBlob = txBlob;
	}
	public TxJson getTxJson() {
		return txJson;
	}
	public void setTxJson(TxJson txJson) {
		this.txJson = txJson;
	}
}
