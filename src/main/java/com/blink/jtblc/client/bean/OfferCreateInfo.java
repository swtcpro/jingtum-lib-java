package com.blink.jtblc.client.bean;

/**
 * 返回挂单创建信息对象
 * 描述：buildOfferCreateTx返回对象
 */
public class OfferCreateInfo {
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
		// 交易费
		private String fee;
		// 交易标记
		private Integer Flags;
		// 单子序列号
		private Integer sequence;
		// 签名公钥
		private String signingPubKey;
		// 对家得到的Object
		private Amount takerGets;
		// 对家支付的Object
		private String takerpays;
		// 时间戳
		private Integer timestamp;
		// 交易类型：TrustSet信任;RelationDel解冻；RelationSet 授权/冻结
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
		public Amount getTakerGets() {
			return takerGets;
		}
		public void setTakerGets(Amount takerGets) {
			this.takerGets = takerGets;
		}
		public String getTakerpays() {
			return takerpays;
		}
		public void setTakerpays(String takerpays) {
			this.takerpays = takerpays;
		}
		public Integer getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Integer timestamp) {
			this.timestamp = timestamp;
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
