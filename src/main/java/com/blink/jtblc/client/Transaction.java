package com.blink.jtblc.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.blink.jtblc.client.bean.AccountInfo;
import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.config.Config;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.core.coretypes.AccountID;
import com.blink.jtblc.core.coretypes.Amount;
import com.blink.jtblc.core.coretypes.uint.UInt32;
import com.blink.jtblc.core.types.known.tx.signed.SignedTransaction;
import com.blink.jtblc.core.types.known.tx.txns.Payment;
import com.blink.jtblc.core.types.known.tx.txns.RelationSet;
import com.blink.jtblc.core.types.known.tx.txns.TrustSet;
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
		String tx_blob = "";
		String type = (String) txJson.get("TransactionType");
		AccountInfo ainfo = remote.requestAccountInfo(account, null, "trust");
		SignedTransaction tx = null;
		switch(type) {
			case "Payment":
				Payment payment = new Payment(remote);
				payment.as(AccountID.Account, account);
				payment.as(AccountID.Destination, to);
				payment.as(Amount.Amount, txJson.get("Amount"));
				if(fee != null) {
					payment.as(Amount.Fee, String.valueOf(fee));
				}else {
					payment.as(Amount.Fee, String.valueOf(Config.FEE));
				}
				payment.sequence(new UInt32(ainfo.getAccountData().getSequence()));
				payment.flags(new UInt32(0));
				payment.addMemo(this.memo);
				tx = payment.sign(secret);
				tx_blob = tx.tx_blob;
				break;
			case "TrustSet":
				TrustSet trustSet = new TrustSet(remote);
				trustSet.as(AccountID.Account, account);
				trustSet.as(Amount.LimitAmount, txJson.get("LimitAmount"));
				if(fee != null) {
					trustSet.as(Amount.Fee, String.valueOf(fee));
				}else {
					trustSet.as(Amount.Fee, String.valueOf(Config.FEE));
				}
				trustSet.sequence(new UInt32(ainfo.getAccountData().getSequence()));
				trustSet.flags(new UInt32(0));
				trustSet.addMemo(memo);
				tx = trustSet.sign(secret);
				tx_blob = tx.tx_blob;
				break;
			case "RelationSet":
				RelationSet relationSet = new RelationSet(remote);
				//relationSet.as(Amount.RelationType, txJson.get("RelationType"));
				relationSet.as(AccountID.Account, account);
				relationSet.as(AccountID.Target, txJson.get("Target"));
				relationSet.as(Amount.LimitAmount, txJson.get("LimitAmount"));
				if(fee != null) {
					relationSet.as(Amount.Fee, String.valueOf(fee));
				}else {
					relationSet.as(Amount.Fee, String.valueOf(Config.FEE));
				}
				relationSet.sequence(new UInt32(ainfo.getAccountData().getSequence()));
				relationSet.flags(new UInt32(0));
				relationSet.addMemo(memo);
				tx = relationSet.sign(secret);
				tx_blob = tx.tx_blob;
				break;
			case "RelationDel":
			default :
				break;
		}
		return tx_blob;
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
		//System.out.println("参数：" + JsonUtils.toJsonString(params));
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
