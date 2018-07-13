package com.blink.jtblc.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.exceptions.RemoteException;
import com.blink.jtblc.exceptions.TransactionException;
import com.blink.jtblc.utils.JsonUtils;

// 交易信息
public class Transaction {
	private Remote remote;
	// 发起账号
	private String account;
	// 目标账号
	private String to;
	// 支付金额对象
	private AmountInfo amount;
	// 支付金额对象
	private Object amountObj;
	// 关系金额对象
	private AmountInfo limit;
	// 挂单方支付金额对象
	private AmountInfo  takerGets;
	// 挂单方获得金额对象
	private AmountInfo takerPays;
	// 私钥
	private String secret;
	// 备注信息
	private String memo;
	private List<String> memos = new ArrayList();
	// 交易签名
	private String txnSignature;
	// 关系种类
	private String relationType;
	// 属性种类
	private String propertyType;
	// 挂单类型
	private String flags;
	// 取消的单子号
	private Integer sequence;
	// command
	private String command;
	private Integer fee;
	// tx_json
	private Map txJson = new HashMap();
	// 交易类型：Payment
	private String TransactionType;
	
	public Transaction() {
	}
	
	public Transaction(Remote remote) {
		this.remote = remote;
	}
	
	public Object getAmountObj() {
		return amountObj;
	}
	
	public void setAmountObj(Object amountObj) {
		this.amountObj = amountObj;
	}
	
	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}
	
	public Remote getRemote() {
		return remote;
	}
	
	public void setRemote(Remote remote) {
		this.remote = remote;
	}
	
	public String getAccount() {
		// tx_json.get("Account")
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public AmountInfo getAmount() {
		return amount;
	}
	
	public void setAmount(AmountInfo amount) {
		this.amount = amount;
	}
	
	public AmountInfo getLimit() {
		return limit;
	}
	
	public void setLimit(AmountInfo limit) {
		this.limit = limit;
	}
	
	public AmountInfo getTakerGets() {
		return takerGets;
	}
	
	public void setTakerGets(AmountInfo takerGets) {
		this.takerGets = takerGets;
	}
	
	public AmountInfo getTakerPays() {
		return takerPays;
	}
	
	public void setTakerPays(AmountInfo takerPays) {
		this.takerPays = takerPays;
	}
	
	public String getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getTxnSignature() {
		return txnSignature;
	}
	
	public void setTxnSignature(String txnSignature) {
		this.txnSignature = txnSignature;
	}
	
	public String getRelationType() {
		return relationType;
	}
	
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	public String getPropertyType() {
		return propertyType;
	}
	
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	
	public String getFlags() {
		return flags;
	}
	
	public void setFlags(String flags) {
		this.flags = flags;
		this.txJson.put("Flags", flags);
	}
	
	public Integer getSequence() {
		return sequence;
	}
	
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public Map getTxJson() {
		return txJson;
	}
	
	public void setTxJson(Map txJson) {
		this.txJson = txJson;
	}
	
	/**
	 * 添加备注信息
	 * 
	 * @param memo
	 */
	public void addMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * 签名
	 * 
	 * @param sercet
	 * @return
	 */
	public String sign(String secret) {
//		// 1.获取账号信息,钱包信息--待完善
//		// String rs = remote.requestAccountTums(account);
//		// var req = remote.requestAccountInfo({account: self.tx_json.Account, type: 'trust'});
//		// public AccountInfo requestAccountInfo(String account, Object ledger, String type) {
////		AccountInfo accountInfo = this.remote.requestAccountInfo(this.account, null, "trust");
//		this.txJson.put("sequence", accountInfo.getAccountData().getSequence());
//		this.txJson.put("fee", Double.parseDouble(txJson.get("fee").toString()) / 1000000);
//		// payment
//		if (txJson.get("Amount") != null && txJson.get("Amount") instanceof String) {// 基础货币
//			txJson.put("Amount", Double.parseDouble(txJson.get("Amount").toString()) / 1000000);
//		}
//		// payment
//		if (txJson.get("Memos") != null) {
//			List<Map> memos = (List) txJson.get("Memos");
//			Map memo = memos.get(0);
//			Map memoIn = (Map) memo.get("Memo");
//			String memoData = (String) memoIn.get("MemoData");
//			memoData = Utils.hexToString(memoData);
//			memoIn.put("MemoData", memoData);
//		}
//		if (txJson.get("SendMax") != null && txJson.get("SendMax") instanceof String) {
//			txJson.put("SendMax", Double.parseDouble(txJson.get("SendMax").toString()) / 1000000);
//		}
//		// order
//		if (txJson.get("TakerPays") != null && txJson.get("TakerPays") instanceof String) {
//			txJson.put("TakerPays", Double.parseDouble(txJson.get("TakerPays").toString()) / 1000000);
//		}
//		if (txJson.get("TakerGets") != null && txJson.get("TakerGets") instanceof String) {
//			txJson.put("TakerGets", Double.parseDouble(txJson.get("TakerGets").toString()) / 1000000);
//		}
//		Wallet wallet = new Wallet(this.secret);
////		txJson.put("SigningPubKey", wallet.getPublicKey());
////		Integer prefix = 0x53545800;
////		txJson.hashCode()
////		var hash = jser.from_json(self.tx_json).hash(prefix);
////		self.tx_json.TxnSignature = wt.signTx(hash);
////		self.tx_json.blob = jser.from_json(self.tx_json).to_hex();
////		self._local_sign = true;
////		callback(null, self.tx_json.blob);
		return "对交易签名";
	}
	
	/**
	 * 设置费用
	 * 
	 * @param fee
	 */
	public void setFee(String fee) {
		int feeInt = Integer.parseInt(fee);
		if (feeInt < 10) {
			throw new RemoteException("fee is too low");
		}
		this.txJson.put("Fee", fee);
		this.fee = feeInt;
	}
	
	public Integer getFee() {
		return fee;
	}
	
	public void setFee(Integer fee) {
		if (fee < 10) {
			throw new RemoteException("fee is too low");
		}
		this.fee = fee;
	}
	
	/**
	 * 获取交易类型
	 * 
	 * @return
	 */
	public String getTransactionType() {
		return this.txJson.get("TransactionType") == null ? null : this.txJson.get("TransactionType").toString();
	}
	
	public String getTxBlob() {
		return this.txJson.get("blob") == null ? null : this.txJson.get("blob").toString();
	}
	
	/*
	 * public void setPath(String key){
	 * if (key.length() != 40) {
	 * throw new TransactionException(1002,"invalid path key");
	 * }
	 * JSONObject item = (JSONObject) this.remote.getPaths().get(key);
	 * if (Utils.isNull(item)) {
	 * throw new TransactionException(1003,"non exists path key");
	 * }
	 * if(!item.get("path").toString().equals("[]")){
	 * this.tx_json.put("Paths",JSON.parse(String.valueOf(item.get("path"))));
	 * String amount = MaxAmount(item.get("choice"));
	 * this.tx_json.put("SendMax",amount);
	 * }
	 * }
	 */
	public String MaxAmount(Object amount) {
		try {
			float aa = (float) (Float.parseFloat(amount.toString()) * 1.0001);
			return String.valueOf(aa);
		} catch (Exception e) {
			throw new TransactionException(1004, "invalid amount to max");
		}
	}
	
	private Boolean localSign = false;
	private Connection conn = null;
	
	public Boolean getLocalSign() {
		return localSign;
	}
	
	public void setLocalSign(Boolean localSign) {
		this.localSign = localSign;
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * 提交交易信息
	 * 
	 * @return
	 */
	public String submit(Connection conn, Boolean local_sign, Map params) {
		String tx_json_transactionType = "";
		String tx_json_blob = "WW";
		if (local_sign) {
			// 签名之后传给底层
			tx_json_blob = sign(this.secret);
			params.put("tx_blob", tx_json_blob);
		} else if (tx_json_transactionType.equals("Signer")) {
			// 直接将blob传给底层
			params.put("tx_blob", tx_json_blob);
		} else {
			// 不签名交易传给底层
			params.put("secret", this.getSecret());
			// params.put("tx_json", tx_json);
		}
		params.put("memo", this.memo);
		params.put("command", this.command);
		System.out.println("参数：" + JsonUtils.toJsonString(params));
		String msg = conn.submit(params);
		return msg;
	}
	
	/**
	 * 提交交易信息
	 * 
	 * @return
	 */
	public TransactionInfo submit() {
		Map params = new HashMap();
		String tx_json_blob = null;
		if (remote.getLocalSign() != null && remote.getLocalSign()) {
			// 签名之后传给底层
			params.put("tx_blob", sign(this.secret));
		} else if ("Signer".equals(this.getTransactionType())) {
			// 直接将blob传给底层
			params.put("tx_blob", this.getTxBlob());
		} else {
			// 不签名交易传给底层
			params.put("secret", this.getSecret());
			params.put("tx_json", txJson);
		}
		if (StringUtils.isNotEmpty(memo)) {
			if (memo.length() > 2048) {
				throw new RemoteException("memo is too long");
			} else {
				// tx_json.put("MemoData", __stringToHex(utf8.encode(memo));
			}
		}
		params.put("memo", this.memo);
		params.put("command", this.command);
		params.put("tx_json", txJson);
		String msg = conn.submit(params);
		TransactionInfo bean = JsonUtils.toEntity(msg, TransactionInfo.class);
		return bean;
	}
}
