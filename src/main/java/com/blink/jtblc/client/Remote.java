package com.blink.jtblc.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blink.jtblc.client.bean.*;
import org.apache.commons.lang3.StringUtils;

import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.exceptions.RemoteException;
import com.blink.jtblc.utils.CheckUtils;
import com.blink.jtblc.utils.JsonUtils;

public class Remote {
	private String server = "";
	// 签名默认为false,false需要传入密钥
	private Boolean local_sign = false;
	private Connection conn = null;
	
	public Remote(Connection conn) {
		this.conn = conn;
	}
	
	public Remote(Connection conn, Boolean local_sign) {
		this.conn = conn;
		this.local_sign = local_sign;
	}
	
	/**
	 * 工具方法：组装发送前的信息
	 * 
	 * @param command
	 * @param data
	 * @return
	 */
	public String sendMessage(String command, Map<String, Object> data) {
		Map<String, Object> params = new HashMap();
		params.put("command", command);
		params.putAll(data);
		return conn.submit(params);
	}
	
	/**
	 * 当
	 *
	 * @return
	 */
	public String requestLedgerInfo() {
		Map<String, Object> params = new HashMap();
		params.put("streams", new String[] { "ledger", "server" });
		String msg = sendMessage("subscribe", params);
		return msg;
	}

	//4.2  创建连接
	public String subscribe()throws Exception{
		Map params = new HashMap();
		Map message = new HashMap();
		List<String> strs = new ArrayList<String>();
		strs.add("ledger");
		strs.add("server");
		message.put("streams",strs);
		params.put("message",message);
		String msg = this.sendMessage("server",params);
		return msg;
	}
	/**
	 * 4.4 请求底层服务器信息
	 * @return
	 */
	public ServerInfo requestServerInfo() throws Exception{
		ServerInfo ser =new ServerInfo();
		Map params = new HashMap();
		String msg = this.sendMessage("server_info",params);
		Map map = JsonUtils.toObject(msg,Map.class);
		if(map.get("status").equals("success")){
			Map result = (Map)map.get("result");
			Map info = (Map)result.get("info");
			ser.setVersion(info.get("build_version").toString());
			ser.setLedgers(info.get("complete_ledgers").toString());
			ser.setNode(info.get("pubkey_node").toString());
			ser.setState(info.get("server_state").toString());
		}
		return ser;
	}

	/**
	 * 4.5 获取最新账本信息
	 * @return
	 * @throws Exception
	 */
	public LedgerClosed requestLedgerClosed() throws Exception{
		Map params = new HashMap();
		String msg = this.sendMessage("ledger_closed",params);
		Map map = JsonUtils.toObject(msg,Map.class);
		LedgerClosed ledger = new LedgerClosed();
		if(map.get("status").equals("success")){
			Map result = (Map)map.get("result");
			ledger.setLedgerHash(result.get("ledger_hash").toString());
			ledger.setLedgerIndex(result.get("ledger_index").toString());
		}
		return ledger;
	}

	/**
	 * 4.6 获取某一账本具体信息
	 * 注：整体参数是Object类型，当参数都不填时，默认返回最新账本信息
	 * @param ledger_index    井通区块高度
	 * @param ledger_hash     井通区块hash(与上面ledger_index二选其一)
	 * @param transactions    是否返回账本上的交易记录hash，默认false
	 * @return
	 */
	public Ledger requestLedger(String ledger_index, String ledger_hash, boolean transactions){
		Map params = new HashMap();
		//校验,并将参数写入message对象
		Map message = new HashMap();
		if(StringUtils.isEmpty(ledger_index) && StringUtils.isEmpty(ledger_hash)) {
			message.put("ledger", new RemoteException("ledger_index and ledger_hash is null"));
		}
		if(!CheckUtils.isNumeric(ledger_index)) {
			message.put("ledger_index", new RemoteException("invalid ledger_index"));
		}
		if(!CheckUtils.isValidHash(ledger_hash)) {
			message.put("ledger_hash", new RemoteException("invalid ledger_hash"));
		}
		params.put("message", message);
		params.put("ledger_index",ledger_index);
		params.put("ledger_hash",ledger_hash);
		params.put("transactions",transactions);
//        params.put("command", "ledger");
		String msg = this.sendMessage("ledger",params);
		Map map = JsonUtils.toObject(msg,Map.class);
		Ledger ledger =  new Ledger();
		if(map.get("status").equals("success")){
			Map result = (Map)map.get("result");
			Map ledgerMap = (Map)result.get("ledger");
			ledger.setAccepted((Boolean)ledgerMap.get("accepted"));
			ledger.setLedgerHash(ledgerMap.get("account_hash").toString());
			ledger.setLedgerIndex(ledgerMap.get("ledger_index").toString());
			ledger.setParentHash(ledgerMap.get("parent_hash").toString());
			ledger.setCloseTimeHuman(ledgerMap.get("close_time_human").toString());
			ledger.setTotalCoins(ledgerMap.get("total_coins").toString());
		}
		return ledger;
	}

	/**
	 * 4.7 查询某一交易具体信息
	 *
	 * @param hash 交易hash
	 * @return
	 */
	public Account requestTx(String hash) {
		Map params = new HashMap();
		//校验,并将参数写入message对象
		Map message = new HashMap();
		if(!CheckUtils.isValidHash(hash)) {
			message.put("ledger_hash", new RemoteException("invalid tx hash"));
		}
		message.put("transaction", hash);
		params.put("message", message);
		params.put("transaction", hash);
//        params.put("command", "tx");
		String msg = this.sendMessage("tx",params);
		Account account = JsonUtils.toObject(msg,Account.class);
		return account;
	}
	/**
	 * 4.8 请求账号信息
	 *
	 * @param account 井通钱包地址
	 * @return
	 */
	public AccountInfo requestAccountInfo(String account) {
		String msg =requestAccount("account_info", account, "");
		AccountInfo accountInfo = JsonUtils.toObject(msg,AccountInfo.class);
		return accountInfo;
	}

	/**
	 * 4.9 获得账号可接收和发送的货币
	 *
	 * @param account 井通钱包地址
	 * @return
	 */
	public AccountTums requestAccountTums(String account) {
		String msg = requestAccount("account_currencies", account, "");
		AccountTums accountTums = JsonUtils.toObject(msg,AccountTums.class);
		return accountTums;
	}

	/**
	 * 4.10 获得账号关系
	 *
	 * @param account 井通钱包地址
	 * @param type 关系类型，固定的三个值：trust、authorize、freeze
	 * @return
	 */
	public AccountRelations requestAccountRelations(String account, String type) {
		String command = "";
		if(!CheckUtils.isValidType("relation",type)) {
			throw new RemoteException("invalid relation type");
		}
		switch (type) {
			case "trust":
				command = "account_lines";
			case "authorize":
			case "freeze":
				command = "account_relation";
		}
		String msg = requestAccount(command, account, type);
		AccountRelations accountRelations = JsonUtils.toObject(msg,AccountRelations.class);
		return accountRelations;
	}

	/**
	 * 4.11 获得账号挂单
	 *
	 * @param account 井通钱包地址
	 * @return
	 */
	public AccountOffers requestAccountOffers(String account) {
		String msg = requestAccount("account_offers", account, "");
		AccountOffers accountOffers = JsonUtils.toObject(msg,AccountOffers.class);
		return accountOffers;
	}

	/**
	 * 获取账号信息
	 * inner function
	 * 
	 * @param command
	 * @param account
	 * @param type
	 * @return
	 */
	private String requestAccount(String command, String account, String type) {
		Map params = new HashMap();
		// 校验,并将参数写入message对象
		Map message = new HashMap();
		if (StringUtils.isNotEmpty(type)) {
			Integer relation_type = null;
			switch (type) {
				case "trustline":
					relation_type = 0;
				case "authorize":
					relation_type = 1;
				case "freeze":
					relation_type = 3;
			}
			message.put("relation_type", relation_type);
		}
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid account");
		} else {
			message.put("account", account);
		}
		params.put("message", message);
		params.put("account", account);
		params.put("command", command);
		String msg = this.submit(params);
		return msg;
	}
	
	/**
	 * 获得账号交易列表
	 *
	 * @param account 井通钱包地址
	 * @param limit 限定返回多少条记录，默认200
	 * @return
	 */
	public String requestAccountTx(String account, Integer limit) {
		if (limit == null || limit == 0) {
			limit = 200;
		}
		Map params = new HashMap();
		// 校验,并将参数写入message对象
		Map message = new HashMap();
		message.put("limit", limit);
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("account parameter is invalid");
		} else {
			message.put("account", account);
		}
		params.put("message", message);
		params.put("account", account);
		params.put("command", "account_tx");
		String msg = this.submit(params);
		return msg;
	}
	
	/**
	 * 获得市场挂单列表
	 *
	 * @param getsCurrency 对家想要获得的货币信息：货币种类，三到六个字母或20字节的自定义货币
	 * @param getsIssuer 对家想要获得的货币信息：货币发行方
	 * @param paysCurrency 对家想要支付的货币信息：货币种类，三到六个字母或20字节的自定义货币
	 * @param paysIssuer 对家想要支付的货币信息：货币发行方
	 * @param limit 限定返回多少条记录
	 * @return
	 */
	public String requestOrderBook(Amount taker_gets, Amount taker_pays, Integer limit) {
		Map params = new HashMap();
		/*
		 * Map gets = new HashMap();
		 * gets.put("currency", getCurrency);
		 * gets.put("issuer", getsIssuer);
		 * Map pays = new HashMap();
		 * pays.put("currency", paysCurrency);
		 * pays.put("issuer", paysIssuer);
		 * params.put("taker_gets", gets);
		 * params.put("taker_pays", pays);
		 * params.put("command", "book_offers");
		 */
		if (!CheckUtils.isValidAmount(taker_gets)) {
			throw new RemoteException("invalid taker gets amount");
		}
		if (!CheckUtils.isValidAmount(taker_pays)) {
			throw new RemoteException("invalid taker pays amount");
		}
		params.put("limit", limit);
		params.put("taker_gets", taker_gets);
		params.put("taker_pays", taker_pays);
		params.put("command", "book_offers");
		String msg = this.submit(params);
		return msg;
	}
	
	/**
	 * 获取交易对象
	 *
	 * @param account 发起账号
	 * @param to 目标账号
	 * @param amount 支付金额对象Amount
	 * @param value
	 * @param currency
	 * @param issuer
	 * @param secret 密钥
	 * @param memo 备注
	 * @return
	 */
	public String buildPaymentTx(String account, String to, Amount amount, String memo, String secret) {
		Transaction tx = new Transaction();
		tx.setAccount(account);
		tx.setTo(to);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(to)) {
			throw new RemoteException("invalid destination address");
		}
		tx_json.put("TransactionType", "Payment");
		tx_json.put("Account", account);
		/*
		 * Map amount = new HashMap();
		 * amount.put("value", value);
		 * amount.put("currency", currency);
		 * amount.put("issuer", issuer);
		 */
		/*
		 * Amount amount = new Amount();
		 * amount.setCurrency(currency);
		 * amount.setValue(value);
		 * amount.setIssuer(issuer);
		 */
		tx_json.put("Amount", toAmount(amount));
		tx_json.put("Destination", to);
		if (StringUtils.isNotEmpty(memo)) {
			if (memo.length() > 2048) {
				throw new RemoteException("memo is too long");
			} else {
				// tx_json.put("MemoData", __stringToHex(utf8.encode(memo));
			}
		}
		tx.setSecret(secret);
		tx.setCommand("submit");
		tx.addMemo(memo);
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.local_sign, params);
		return msg;
	}
	
	/**
	 * 设置关系
	 *
	 * @param type 关系种类
	 * @param account 设置关系的源账号
	 * @param target 目标账号
	 * @param limit 关系金额 对象Amount
	 * @param secret
	 * @param memo
	 * @return
	 */
	public String buildRelationTx(String type_value, String account, String target, Amount limit, String memo, String secret) {
		Transaction tx = new Transaction();
		// 将参数写入tx对象,方便读取校验
		tx.setAccount(account);
		tx.setTo(target);
		tx.setLimit(limit);
		tx.setRelation_type(type_value);
		tx.setCommand("submit");
		tx.setSecret(secret);
		tx.setMemo(memo);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidType("relation", type_value)) {
			throw new RemoteException("invalid relation type");
		}
		if (StringUtils.isNotEmpty(memo)) {
			if (memo.length() > 2048) {
				throw new RemoteException("memo is too long");
			} else {
				// tx_json.put("MemoData", __stringToHex(utf8.encode(memo));
			}
		}
		tx.setSecret(secret);
		switch (type_value) {
			case "trust":
				return buildTrustSet(tx);
			case "authorize":
			case "freeze":
			case "unfreeze":
				return buildRelationSet(tx);
		}
		throw new RemoteException("build relation set should not go here");
	}
	
	/**
	 * 根据Transaction对象获取trust关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private String buildTrustSet(Transaction tx) {
		String src = tx.getAccount();
		Amount limit = tx.getLimit();
		String quality_out = "";
		String quality_in = "";
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAmount(limit)) {
			throw new RemoteException("invalid amount");
		}
		tx_json.put("TransactionType", "TrustSet");
		tx_json.put("Account", src);
		tx_json.put("LimitAmount", toAmount(limit));
		if (StringUtils.isNotEmpty(quality_in)) {
			tx_json.put("QualityIn", quality_in);
		}
		if (StringUtils.isNotEmpty(quality_out)) {
			tx_json.put("QualityOut", quality_out);
		}
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.local_sign, params);
		return msg;
	}
	
	/**
	 * 根据Transaction对象获取非trust关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private String buildRelationSet(Transaction tx) {
		String src = tx.getAccount();
		Amount limit = tx.getLimit();
		String des = tx.getTo();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(des)) {
			throw new RemoteException("invalid destination address");
		}
		if (!CheckUtils.isValidAmount(limit)) {
			throw new RemoteException("invalid amount");
		}
		// 关系类型：0信任；1授权；3冻结/解冻；
		String type = tx.getRelation_type();
		tx_json.put("TransactionType", type.equals("unfreeze") ? "RelationDel" : "RelationSet");
		tx_json.put("RelationType", type.equals("authorize") ? "1" : "3");
		tx_json.put("Account", src);
		tx_json.put("Target", des);
		tx_json.put("LimitAmount", toAmount(limit));
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.local_sign, params);
		return msg;
	}
	
	/**
	 * 设置账号属性
	 * 
	 * @param type_value 属性种类
	 * @param account 设置属性的源账号
	 * @param set_flag 属性编号
	 * @param secret
	 * @param memo
	 * @return
	 */
	public String buildAccountSetTx(String type_value, String account, String set_flag, String memo, String secret) {
		Transaction tx = new Transaction();
		tx.setAccount(account);
		tx.setProperty_type(type_value);
		tx.setCommand("submit");
		tx.setSecret(secret);
		tx.setMemo(memo);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidType("accountSet", type_value)) {
			throw new RemoteException("invalid account set type");
		}
		switch (type_value) {
			case "property":
				return buildAccountSet(tx);
			case "delegate":
				return buildDelegateKeySet(tx);
			case "signer":
				return buildSignerSet(tx);
		}
		throw new RemoteException("build account set should not go here");
	}
	
	/**
	 * 根据Transaction对象获取property关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private String buildAccountSet(Transaction tx) {
		Map params = new HashMap();
		String src = tx.getAccount();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		tx_json.put("Account", src);
		tx_json.put("TransactionType", "AccountSet");
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.local_sign, params);
		return msg;
	}
	
	/**
	 * 根据Transaction对象获取delegate关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private String buildDelegateKeySet(Transaction tx) {
		Map params = new HashMap();
		String src = tx.getAccount();
		String delegate_key = "";
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(delegate_key)) {
			throw new RemoteException("invalid regular key address");
		}
		tx_json.put("TransactionType", "SetRegularKey");
		tx_json.put("Account", src);
		tx_json.put("RegularKey", delegate_key);
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.local_sign, params);
		return msg;
	}
	
	/**
	 * 根据Transaction对象获取signer关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private String buildSignerSet(Transaction tx) {
		Map params = new HashMap();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.local_sign, params);
		return msg;
	}
	
	/**
	 * 挂单
	 *
	 * @param type 挂单类型，固定的两个值：Buy、Sell
	 * @param account 挂单方账号
	 * @param getsAmount 对方得到的，即挂单方支付的
	 * @param paysAmount 对方支付的，即挂单方获得的
	 * @param secret
	 * @param memo
	 * @return
	 */
	public String buildOfferCreateTx(String type, String account, Amount getsAmount, Amount paysAmount, String memo, String secret) {
		Transaction tx = new Transaction();
		tx.setCommand("submit");
		tx.setSecret(secret);
		tx.setMemo(memo);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidType("offer", type)) {
			throw new RemoteException("invalid offer type");
		}
		if (!CheckUtils.isValidAmountValue(getsAmount.getValue())) {
			throw new RemoteException("invalid to pays amount");
		}
		if (!CheckUtils.isValidAmount(getsAmount)) {
			throw new RemoteException("invalid to pays amount object");
		}
		if (!CheckUtils.isValidAmountValue(paysAmount.getValue())) {
			throw new RemoteException("invalid to gets amount");
		}
		if (!CheckUtils.isValidAmount(paysAmount)) {
			throw new RemoteException("invalid to gets amount object");
		}
		tx_json.put("TransactionType", "OfferCreate");
		tx_json.put("Account", account);
		if (type.equals("Sell")) {
			tx.setFlags(type);
		}
		tx_json.put("TakerPays", toAmount(paysAmount));
		tx_json.put("TakerGets", toAmount(getsAmount));
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.local_sign, params);
		return msg;
	}
	
	/**
	 * 根据金额对象内容返回信息
	 * 货币单位SWT转基本单位
	 * 
	 * @param amount 金额对象
	 * @return
	 */
	private Object toAmount(Amount amount) {
		String value = amount.getValue();
		BigDecimal temp = new BigDecimal(value);
		BigDecimal max_value = new BigDecimal("100000000000");
		String currency = amount.getCurrency();
		if (StringUtils.isNotEmpty(value) && temp.compareTo(max_value) > 0) {
			throw new RemoteException("invalid amount: amount's maximum value is 100000000000");
		}
		if (currency.equals("SWT")) {
			BigDecimal exchange_rate = new BigDecimal("1000000.00");
			BigDecimal rs = temp.multiply(exchange_rate);
			return String.valueOf(rs.intValue());
		}
		return amount;
	}
	
	/**
	 * 取消挂单
	 *
	 * @param account 挂单方账号
	 * @param sequence 取消的单子号
	 * @param secret
	 * @param memo
	 * @return
	 */
	public String buildOfferCancelTx(String account, Integer sequence, String memo, String secret) {
		Transaction tx = new Transaction();
		tx.setCommand("submit");
		tx.setSecret(secret);
		tx.setMemo(memo);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		tx_json.put("TransactionType", "OfferCancel");
		tx_json.put("Account", account);
		tx_json.put("OfferSequence", sequence);
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.local_sign, params);
		return msg;
	}
	
	/**
	 * 提交信息
	 * 
	 * @return
	 */
	public String submit(Map params) {
		params.remove("message");
		System.out.println("参数：" + JsonUtils.toJsonString(params));
		String msg = this.conn.submit(params);
		return msg;
	}
	
	/************ setter and getter ************/
	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public Boolean getLocal_sign() {
		return local_sign;
	}
	
	public void setLocal_sign(Boolean local_sign) {
		this.local_sign = local_sign;
	}
}
