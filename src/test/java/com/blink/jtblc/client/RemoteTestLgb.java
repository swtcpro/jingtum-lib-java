package com.blink.jtblc.client;

import org.junit.Test;

import com.blink.jtblc.client.bean.LedgerInfo;
import com.blink.jtblc.client.bean.ServerInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.blink.jtblc.utils.JsonUtils;

public class RemoteTestLgb {
	// 井通节点websocket链接：
	// ws://ts5.jingtum.com:5020
	String server = "ws://ts5.jingtum.com:5020";
	String account_1 = "jGmrQ6aiiHBSG1F7ESP4Qw9g4uMY2BfNnX";// fee_account_swt
	String account_2 = "jL8L5f56zjFfmBBq19nvDTuVRfAQ7n19aJ";// issuerop_account
	String secret = "ssCXga6r2x398DdLyN3XkLDiT2ZuS";// bad secret
	Integer limit_num = 10;
	Connection conn = ConnectionFactory.getCollection(server);
	Remote remote = new Remote(conn);
	
	@Test
	public void requestLedgerInfo() {
		LedgerInfo res = remote.requestLedgerInfo();
		System.out.println("requestLedgerInfo:" + JsonUtils.toJsonString(res));
	}
	
	// 4.4 请求底层服务器信息
	@Test
	public void requestServerInfoTest() {
		ServerInfo res = remote.requestServerInfo();
		System.out.println("requestServerInfoTest:" + JsonUtils.toJsonString(res));
	}
	
	// 4.5 获取最新账本信息
	@Test
	public void requestLedgerClosedTest() {
		String msg = remote.requestLedgerClosed();
		System.out.println("requestLedgerClosedTest:" + msg);
	}
	//
	// // 4.6 获取某一账本具体信息
	// @Test
	// public void requestLedgerTest() {
	// String ledger_index = "8488670";// 8488670
	// String ledger_hash = "";// 9A29EE3DF103B9FED0CF20DB334FD93DF9232647AC23F9B9F133622FAAECA5F1
	// boolean transactions = true;
	// String msg = remote.requestLedger(ledger_index, ledger_hash, transactions);
	// System.out.println("requestLedgerTest:" + msg);
	// }
	//
	// // 4.7 查询某一交易具体信息
	// @Test
	// public void requestTxTest() {
	// String hash = "C57A26E656CF5DF389FCC154BA14F2845420DB88A102F0D86305E4FFE48EDA5D";
	// String msg = remote.requestTx(hash);
	// System.out.println("requestTxTest:" + msg);
	// }
	//
	// // 4.8 请求账号信息
	// @Test
	// public void requestAccountInfoTest() {
	// String msg = remote.requestAccountInfo(account_1);
	// System.out.println("requestAccountInfoTest:" + msg);
	// }
	//
	// // 4.9 获得账号可接收和发送的货币
	// @Test
	// public void requestAccountTumsTest() {
	// String msg = remote.requestAccountTums(account_1);
	// System.out.println("requestAccountTumsTest:" + msg);
	// }
	//
	// // 4.10 获得账号关系
	// @Test
	// public void requestAccountRelationsTest() {
	// String type = "trust";
	// String msg = remote.requestAccountRelations(account_1, type);
	// System.out.println("requestAccountRelationsTest:" + msg);
	// }
	//
	// // 4.11 获得账号挂单
	// @Test
	// public void requestAccountOffersTest() {
	// String msg = remote.requestAccountOffers(account_1);
	// System.out.println("requestAccountOffersTest:" + msg);
	// }
	//
	// // 4.12 获得账号交易列表--OK
	// @Test
	// public void requestAccountTxTest() {
	// String msg = remote.requestAccountTx(account_1, limit_num);
	// System.out.println("requestAccountTxTest:" + msg);
	// }
	//
	// // 4.13 获得市场挂单列表--OK
	// @Test
	// public void requestOrderBookTest() {
	// String getsCurrency = "SWT";
	// String getsIssuer = "";
	// String paysCurrency = "CNY";
	// String paysIssuer = "jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or";
	// Amount taker_gets = new Amount();
	// taker_gets.setCurrency(getsCurrency);
	// taker_gets.setIssuer(getsIssuer);
	// Amount taker_pays = new Amount();
	// taker_pays.setCurrency(paysCurrency);
	// taker_pays.setIssuer(paysIssuer);
	// String msg = remote.requestOrderBook(taker_gets, taker_pays, limit_num);
	// System.out.println("requestOrderBookTest:" + msg);
	// }
	//
	// // 4.14 支付
	// @Test
	// public void buildPaymentTxTest() {
	// // 使用签名
	// // Remote remote = new Remote(conn,true);
	// // address=jLRndNqkyi4DasqG3pjA3pcAcsnkSKoTdU
	// // secret=ssCXga6r2x398DdLyN3XkLDiT2ZuS
	// // msg = remote.requestAccountInfo("jLRndNqkyi4DasqG3pjA3pcAcsnkSKoTdU");//19999940
	// // msg = remote.requestAccountInfo("jJHdzzHwrPggaBKBZZWbC6snJneSvzip5C");//48099931
	// String account = "jGmrQ6aiiHBSG1F7ESP4Qw9g4uMY2BfNnX";
	// // SWT支付
	// String to = "jL8L5f56zjFfmBBq19nvDTuVRfAQ7n19aJ";
	// String value = "0.000005";
	// String currency = "SWT";
	// String issuer = "";
	// // 其他货币支付
	// /*
	// * String value = "1";
	// * String currency = "CNY";
	// * String issuer = "jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or";
	// */
	// Amount amount = new Amount();
	// amount.setCurrency(currency);
	// amount.setValue(value);
	// amount.setIssuer(issuer);
	// String memo = "给jJHdzzHwrPggaBKBZZWbC6snJneSvzip5C支付0.000005swt.";
	// String msg = remote.buildPaymentTx(account, to, amount, memo, secret);
	// System.out.println("buildPaymentTx:" + msg);
	// }
	//
	// // 4.15 设置关系
	// @Test
	// public void buildRelationTxTest() {
	// String type = "trust";
	// String target = "jL8L5f56zjFfmBBq19nvDTuVRfAQ7n19aJ";
	// String value = "0.00005";
	// String currency = "SWT";
	// String issuer = "";
	// Amount limit = new Amount();
	// limit.setValue(value);
	// limit.setCurrency(currency);
	// limit.setIssuer(issuer);
	// String memo = "4.15 设置关系";
	// String msg = remote.buildRelationTx(type, account_1, target, limit, memo, secret);
	// System.out.println("buildRelationTx:" + msg);
	// }
	//
	// // 4.16 设置账号属性
	// @Test
	// public void buildAccountSetTxTest() {
	// String type = "property";
	// String set_flag = "";
	// // 可选
	// String memo = "4.16 设置账号属性";
	// String msg = remote.buildAccountSetTx(type, account_1, set_flag, memo, secret);
	// System.out.println("buildAccountSetTx:" + msg);
	// }
	//
	// // 4.17 挂单
	// @Test
	// public void buildOfferCreateTxTest() {
	// String type = "Sell";
	// Amount getsAmount = new Amount();
	// getsAmount.setValue("1");
	// getsAmount.setIssuer("");
	// getsAmount.setCurrency("SWT");
	// Amount paysAmount = new Amount();
	// paysAmount.setValue("0.01");
	// paysAmount.setIssuer("jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or");
	// paysAmount.setCurrency("CNY");
	// // 可选
	// String memo = "4.17 挂单";
	// String msg = remote.buildOfferCreateTx(type, account_1, getsAmount, paysAmount, memo, secret);
	// System.out.println("buildOfferCreateTx:" + msg);
	// }
	//
	// // 4.18 取消挂单
	// @Test
	// public void buildOfferCancelTxTest() {
	// Integer sequence = 19;
	// // 可选
	// String memo = "4.18 取消挂单";
	// String msg = remote.buildOfferCancelTx(account_1, sequence, memo, secret);
	// // 可选
	// System.out.println("buildOfferCancelTx:" + msg);
	// }
}