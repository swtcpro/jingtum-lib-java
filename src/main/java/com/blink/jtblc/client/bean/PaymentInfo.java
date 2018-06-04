package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 返回的支付信息对象
 * 描述：buildPaymentTx返回对象
 */
public class PaymentInfo {
	
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
	

	/**
	 * 交易内容 
	 *
	 */
	class TxJson{
		// 账号地址 
		private String  account;
		// 交易金额 
		private String amount;
		// 对家
		private String destination;
		// 交易费
		private String fee;
		// 交易标记
		private Integer Flags;
		// 备注
		private List<String> memos;
		// 单子序列号
		private Integer sequence;
		// 签名公钥 
		private String signingPubKey;
		// 交易类型
		private String transactionType;
		// 交易签名
		private String txnSignature;
		// 交易hash
		private String hash;
		
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public String getFee() {
			return fee;
		}
		public void setFee(String fee) {
			this.fee = fee;
		}
		public Integer getFlags() {
			return Flags;
		}
		public void setFlags(Integer flags) {
			Flags = flags;
		}
		public List<String> getMemos() {
			return memos;
		}
		public void setMemos(List<String> memos) {
			this.memos = memos;
		}
		public Integer getSequence() {
			return sequence;
		}
		public void setSequence(Integer sequence) {
			this.sequence = sequence;
		}
		public String getSigningPubKey() {
			return signingPubKey;
		}
		public void setSigningPubKey(String signingPubKey) {
			this.signingPubKey = signingPubKey;
		}
		public String getTransactionType() {
			return transactionType;
		}
		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}
		public String getTxnSignature() {
			return txnSignature;
		}
		public void setTxnSignature(String txnSignature) {
			this.txnSignature = txnSignature;
		}
		public String getHash() {
			return hash;
		}
		public void setHash(String hash) {
			this.hash = hash;
		}
	}
}
