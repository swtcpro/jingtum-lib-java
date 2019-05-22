package com.blink.jtblc.client;

import java.math.BigDecimal;

import com.blink.jtblc.client.Remote;
import com.blink.jtblc.client.Transaction;
import com.blink.jtblc.client.bean.AccountOffers;
import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.BookOffer;
import com.blink.jtblc.client.bean.BookOffers;
import com.blink.jtblc.client.bean.Offer;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.blink.jtblc.utils.JsonUtils;

public class OfferTest {

	 static String server = "wss://s.jingtum.com:5020";// 生产环境
//	static String server = "ws://ts5.jingtum.com:5020";// 测试环境
	static Boolean local_sign = false;// 是否使用本地签名方式提交交易
	static Connection conn = ConnectionFactory.getCollection(server);
	static Remote remote = new Remote(conn, local_sign);

	public static void main(String[] args) {
//		createOffer();//创建订单
		getAccountOffers();//获取挂单列表
//		cancelOffer();//取消挂单
//		getOffers();
	}

	/**
	 * 创建订单
	 * @return
	 */
	public static String createOffer() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		String secret = "ssWiEpky7Bgj5GFrexxpKexYkeuUv";
		AmountInfo getsAmount = new AmountInfo();
		getsAmount.setValue("0.000009");
		getsAmount.setIssuer("");
		getsAmount.setCurrency("SWT");
		AmountInfo paysAmount = new AmountInfo();
		paysAmount.setValue("0.02");
		paysAmount.setIssuer("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		paysAmount.setCurrency("CNY");
		Transaction tx = remote.buildOfferCreateTx("Sell", account, getsAmount, paysAmount);
		tx.setSecret(secret);
		tx.addMemo(null);
		TransactionInfo bean = tx.submit();
		if ("0".equals(bean.getEngineResultCode())) {
			System.out.println("创建挂单成功 Hash："+bean.getTxJson().getHash());
			return bean.getTxJson().getHash();
		} else {
			System.out.println("创建挂单失败 Message："+bean.getEngineResult());
			return bean.getEngineResult();
		}
	}

	/**
	 * 取消订单
	 * @param account
	 * @return
	 */
	public static String cancelOffer() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		String secret = "ssWiEpky7Bgj5GFrexxpKexYkeuUv";
		Integer sequence = 35;
		Transaction tx = remote.buildOfferCancelTx(account, sequence);
		tx.setSecret(secret);
		TransactionInfo bean = tx.submit();
		if ("0".equals(bean.getEngineResultCode())) {
			System.out.println("取消挂单成功 Hash："+bean.getTxJson().getHash());
			return bean.getTxJson().getHash();
		} else {
			System.out.println("创建挂单失败 Message："+bean.getEngineResult());
			return bean.getEngineResult();
		}
	}
	/**
	 * 获取账号订单列表
	 * @param account
	 * @return
	 */
	public static AccountOffers getAccountOffers() {
		String account = "jPy7cSjzvooDK6Bv2FnTM8Legzb7dzyKAb";
		AccountOffers bean = remote.requestAccountOffers(account, null);
		System.out.println(JsonUtils.toJsonString(bean));
		for(Offer offer : bean.getOffers()){
			System.out.println("挂单编号："+offer.getSeq());
			System.out.println("get："+offer.getTakerGets().getCurrency()+":"+offer.getTakerGets().getValue());
			System.out.println("pay："+offer.getTakerPays().getCurrency()+":"+offer.getTakerPays().getValue());
			System.out.println("====================");
		}
		return bean;
	}
	
	/**
	 * 获取市场订单列表
	 * @param account
	 * @return
	 */
	public static void getOffers() {
		AmountInfo gets = new AmountInfo();
		gets.setCurrency("SWT");
		gets.setIssuer("");
		AmountInfo pays = new AmountInfo();
		pays.setCurrency("CNY");
		pays.setIssuer("jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or");
		BookOffers bean = remote.requestOrderBook(gets, pays, 10);
		System.out.println(JsonUtils.toJsonString(bean));
		for(BookOffer offer : bean.getOffers()){
			System.out.println("挂单账户："+offer.getAccount()+offer.getTakerGets());
			System.out.println("get："+offer.getTakerGets().getCurrency()+":"+offer.getTakerGets().getValue());
			System.out.println("pay："+offer.getTakerPays().getCurrency()+":"+offer.getTakerPays().getValue());
			
			BigDecimal pay = new BigDecimal(offer.getTakerPays().getValue());
			BigDecimal get = new BigDecimal(offer.getTakerGets().getValue());
			BigDecimal rs = pay.divide(get,6,BigDecimal.ROUND_HALF_UP);
			System.out.println("单价："+rs.toString());
			System.out.println("====================");
		}
	}
	
	

}
