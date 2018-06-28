package com.blink.jtblc.client;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blink.jtblc.client.bean.Account;
import com.blink.jtblc.client.bean.AccountInfo;
import com.blink.jtblc.client.bean.AccountOffers;
import com.blink.jtblc.client.bean.AccountPropertyInfo;
import com.blink.jtblc.client.bean.AccountRelations;
import com.blink.jtblc.client.bean.AccountTums;
import com.blink.jtblc.client.bean.AccountTx;
import com.blink.jtblc.client.bean.Amount;
import com.blink.jtblc.client.bean.BookOffers;
import com.blink.jtblc.client.bean.Ledger;
import com.blink.jtblc.client.bean.LedgerClosed;
import com.blink.jtblc.client.bean.LedgerInfo;
import com.blink.jtblc.client.bean.OfferCancelInfo;
import com.blink.jtblc.client.bean.OfferCreateInfo;
import com.blink.jtblc.client.bean.PaymentInfo;
import com.blink.jtblc.client.bean.RelationInfo;
import com.blink.jtblc.client.bean.ServerInfo;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.blink.jtblc.utils.JsonUtils;

public class RemoteTest {
	protected static final Logger log = LoggerFactory.getLogger(RemoteTest.class);
	// 井通节点websocket链接：
	// 子账号
	String account_1 = "jK4kdiriyxErTfW8wMMjzP25oT2AKLWGfY";
	String secret_1 = "shrh9UUHLfDfEiByzakXu6Qorf2T7";
	// 主账号
	String account_2 = "j47gDd3ethDU4UJMD2rosg9WrSXeh9bLd1";
	String secret_2 = "shFsfC6b1GCfxHn4Y4b3TSALHo37i";
	Integer limit_num = 100;
	// ws://ts5.jingtum.com:5020
	String server = "ws://101.200.176.238:5020";
	Connection conn = ConnectionFactory.getCollection(server);
	Remote remote = new Remote(conn);
	
	// @Test
	public void testSl4j() {
		log.debug("sl4j.debug...");
		log.info("sl4j.info");
		log.error("sl4j.error");
	}
	
	/**
	 * 4.1 创建Remote对象
	 */
	public Remote initConnection() {
		Connection conn = ConnectionFactory.getCollection(server);
		Remote remote = new Remote(conn);
		return remote;
	}
	
	/**
	 * 4.2建立连接后，查询节点信息及帐本信息。
	 */
	// @Test
	public void requestLedgerInfo() {
		// Remote remote = initConnection();
		LedgerInfo res = remote.requestLedgerInfo();
		System.out.println("4.2 requestLedgerInfo:\n" + JsonUtils.toJsonString(res));
	}
	
	/**
	 * 4.3 关闭websocket链接
	 */
	// @Test
	public void closeConnection() {
		conn.close();
	}
	
	// 4.4 请求底层服务器信息
	// @Test
	public void requestServerInfoTest() {
		ServerInfo bean = remote.requestServerInfo();
		System.out.println("state:" + bean.getServerState());
		System.out.println("4.4 requestServerInfoTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.5 获取最新账本信息
	// @Test
	public void requestLedgerClosedTest() throws Exception {
		LedgerClosed bean = remote.requestLedgerClosed();
		System.out.println("ledger_hash:" + bean.getLedgerHash());
		System.out.println("4.5 requestLedgerClosedTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.6 获取某一账本具体信息
	// @Test
	public void requestLedgerTest() {
		String ledger_index = "521146";
		String ledger_hash = "DB48A226AFFFDF02A782757DE55680905556EE4DBCFE3ACDA06D3B69481DF2121";
		boolean transactions = true;
		// ledger_index
		Ledger bean = remote.requestLedger(ledger_hash, transactions);
		System.out.println("requestLedgerTest-ledgerIndex:" + bean.getLedgerIndex());
		System.out.println("4.6 requestLedgerTest:\n" + JsonUtils.toJsonString(bean));
		ledger_index = "5211461";
		bean = remote.requestLedger(ledger_index, transactions);
		System.out.println("requestLedgerTest-ledgerIndex:" + bean.getLedgerIndex());
		System.out.println("4.6 requestLedgerTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.7 查询某一交易具体信息
	// @Test
	public void requestTxTest() {
		String hash = "162D46BFD8A001D96F8E440B8A75E2EF5CD22538C1EDB039389AE589914E70A8";
		Account bean = remote.requestTx(hash);
		System.out.println("requestTxTest:" + bean.getAccount());
		System.out.println("4.7 requestTxTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.8 请求账号信息
	// @Test
	public void requestAccountInfoTest() {
		AccountInfo bean = remote.requestAccountInfo(account_1);
		System.out.println("账号金额：" + bean.getAccountData().getBalance());
		System.out.println("4.8 requestAccountInfoTest:\n" + JsonUtils.toJsonString(bean));
		bean = remote.requestAccountInfo(account_2);
		System.out.println("账号金额：" + bean.getAccountData().getBalance());
		System.out.println("4.8 requestAccountInfoTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.9 获得账号可接收和发送的货币
	// @Test
	public void requestAccountTumsTest() {
		AccountTums bean = remote.requestAccountTums(account_2);
		System.out.println("requestAccountTumsTest:" + bean.getLedgerIndex());
		System.out.println("4.9 requestAccountTumsTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.10 获得账号关系
	// @Test
	public void requestAccountRelationsTest() {
		String type = "trust";
		AccountRelations bean = null;
		// bean = remote.requestAccountRelations(account_1, type);
		// System.out.println("4.10 requestAccountRelationsTest:\n" + JsonUtils.toJsonString(bean));
		type = "authorize";
		// bean = remote.requestAccountRelations(account_1, type);
		// System.out.println("4.10 requestAccountRelationsTest:\n" + JsonUtils.toJsonString(bean));
		type = "freeze";
		bean = remote.requestAccountRelations(account_1, type);
		System.out.println("4.10 requestAccountRelationsTest:\n" + JsonUtils.toJsonString(bean));
		// 信任(trust)、授权(authorize)、冻结(freeze)。
	}
	
	// 4.11 获得账号挂单
	// @Test
	public void requestAccountOffersTest() {
		AccountOffers bean = remote.requestAccountOffers("jB7rxgh43ncbTX4WeMoeadiGMfmfqY2xLZ");
		System.out.println("4.11 requestAccountOffersTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.12 获得账号交易列表
	// @Test
	public void requestAccountTxTest() {
		AccountTx bean = remote.requestAccountTx("jB7rxgh43ncbTX4WeMoeadiGMfmfqY2xLZ", limit_num);
		System.out.println("4.12 requestAccountTxTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.13 获得市场挂单列表
	// @Test
	public void requestOrderBookTest() {
		String getsCurrency = "SWT";
		String getsIssuer = "";
		String paysCurrency = "CNY";
		String paysIssuer = "jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS";
		Amount taker_gets = new Amount();
		taker_gets.setCurrency(getsCurrency);
		taker_gets.setIssuer(getsIssuer);
		Amount taker_pays = new Amount();
		taker_pays.setCurrency(paysCurrency);
		taker_pays.setIssuer(paysIssuer);
		BookOffers bean = remote.requestOrderBook(taker_gets, taker_pays, limit_num);
		System.out.println("4.13 requestOrderBookTest:\n" + JsonUtils.toJsonString(bean));
	}
	
	// 4.14 支付
	// @Test
	public void buildPaymentTxTest() {
		// 使用签名
		// Remote remote = new Remote(conn,true);
		// SWT支付
		String value = "0.000001";
		String currency = "SWT";
		String issuer = "";
		// 其他货币支付
		// String value = "1";
		// String currency = "CNY";
		// String issuer = "jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or";
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setValue(value);
		amount.setIssuer(issuer);
		String memo = "支付";
		PaymentInfo bean = remote.buildPaymentTx(account_2, account_1, amount, memo, secret_2);
		System.out.println("支付：" + bean.getEngineResultMessage());
	}
	
	// 4.15 设置关系
	// @Test
	public void buildRelationTxTest() {
		String type = "trust";
		String value = "0.00005";
		String currency = "SWT";
		String issuer = "";
		Amount limit = new Amount();
		limit.setValue(value);
		limit.setCurrency(currency);
		limit.setIssuer(issuer);
		String memo = "4.15 设置关系";
		RelationInfo bean = remote.buildRelationTx(type, account_2, account_1, limit, memo, secret_2);
		System.out.println("buildRelationTx:" + bean.getEngineResultMessage());
	}
	
	// 4.16 设置账号属性
	// @Test
	public void buildAccountSetTxTest() {
		String type = "property";
		String set_flag = "";
		// 可选
		String memo = "4.16 设置账号属性";
		AccountPropertyInfo bean = remote.buildAccountSetTx(type, account_1, set_flag, memo, secret_1);
		System.out.println("buildAccountSetTx:" + bean.getEngineResultMessage());
	}
	
	// 4.17 挂单
	// @Test
	public void buildOfferCreateTxTest() {
		String type = "Sell";
		Amount getsAmount = new Amount();
		getsAmount.setValue("0.000001");
		getsAmount.setIssuer("");
		getsAmount.setCurrency("SWT");
		Amount paysAmount = new Amount();
		paysAmount.setValue("0.01");
		paysAmount.setIssuer("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		paysAmount.setCurrency("CNY");
		// 可选
		String memo = "4.17 挂单";
		OfferCreateInfo bean = remote.buildOfferCreateTx(type, account_2, getsAmount, paysAmount, memo, secret_2);
		System.out.println("buildOfferCreateTx:" + bean.getEngineResultMessage());
	}
	
	// 4.18 取消挂单
	// @Test
	public void buildOfferCancelTxTest() {
		Integer sequence = 22;
		// 可选
		String memo = "4.18 取消挂单";
		OfferCancelInfo bean = remote.buildOfferCancelTx(account_2, sequence, memo, secret_2);
		// 可选
		System.out.println("buildOfferCancelTx:" + bean.getEngineResultMessage());
	}
	
	// 4.14-支付方法new
	// @Test
	public void buildPaymentTxTest_new() {
		// 使用签名
		// Remote remote = new Remote(conn,true);
		// SWT支付
		String value = "0.000001";
		String currency = "SWT";
		String issuer = "";
		// 其他货币支付
		// String value = "1";
		// String currency = "CNY";
		// String issuer = "jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or";
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setValue(value);
		amount.setIssuer(issuer);
		String memo = "支付";
		Transaction tx = remote.buildPaymentTx(account_2, account_1, amount);
		tx.addMemo(memo);
		tx.setSecret(secret_2);
		TransactionInfo bean = tx.submit();
		System.out.println("buildPaymentTxTest_new:" + bean.getEngineResultMessage());
	}
	
	// 4.15 设置关系-new
	// @Test
	public void buildRelationTxTest_new() {
		String type = "freeze";
		String value = "0.000001";
		String currency = "SWT";
		String issuer = "";
		Amount limit = new Amount();
		limit.setValue(value);
		limit.setCurrency(currency);
		limit.setIssuer(issuer);
		String memo = "4.15 设置关系";
		Transaction tx = remote.buildRelationTx(type, account_2, account_1, limit);
		tx.addMemo(memo);
		tx.setSecret(secret_2);
		TransactionInfo bean = tx.submit();
		System.out.println("buildRelationTxTest_new:" + bean.getEngineResultMessage());
	}
	
	// 4.16 设置账号属性-new 
	// @Test
	public void buildAccountSetTxTest_new() {
		String type = "property";
		// 可选
		String memo = "4.16 设置账号属性";
		Transaction tx = remote.buildAccountSetTx(type, account_2);
		tx.addMemo(memo);
		tx.setSecret(secret_2);
		TransactionInfo bean = tx.submit();
		System.out.println("buildRelationTxTest_new:" + bean.getEngineResultMessage());
	}
	
	// 4.17 挂单-new
	@Test
	public void buildOfferCreateTxTest_new() {
		String type = "Sell";
		Amount getsAmount = new Amount();
		getsAmount.setValue("0.000001");
		getsAmount.setIssuer("");
		getsAmount.setCurrency("SWT");
		Amount paysAmount = new Amount();
		paysAmount.setValue("0.01");
		paysAmount.setIssuer("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		paysAmount.setCurrency("CNY");
		// 可选
		String memo = "4.17 挂单";
		Transaction tx = remote.buildOfferCreateTx(type, account_2, getsAmount, paysAmount);
		tx.addMemo(memo);
		tx.setSecret(secret_2);
		TransactionInfo bean = tx.submit();
		System.out.println("buildOfferCreateTxTest_new:" + bean.getTxJson().getSequence());
	}
	
	// 4.18 取消挂单-new
	// @Test
	public void buildOfferCancelTxTest_new() {
		Integer sequence = 103;
		// 可选
		String memo = "4.18 取消挂单";
		Transaction tx = remote.buildOfferCancelTx(account_2, sequence);
		tx.addMemo(memo);
		tx.setSecret(secret_2);
		TransactionInfo bean = tx.submit();
		System.out.println("buildOfferCancelTxTest_new:" + bean.getEngineResultMessage());
	}
}