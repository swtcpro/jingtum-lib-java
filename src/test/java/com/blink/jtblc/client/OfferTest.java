package com.blink.jtblc.client;

import com.blink.jtblc.client.Remote;
import com.blink.jtblc.client.Transaction;
import com.blink.jtblc.client.bean.AccountOffers;
import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.Offer;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.blink.jtblc.utils.JsonUtils;
/**
 * 挂单相关测试
 * @author yyl
 * @date 2019年5月17日
 */
public class OfferTest {

	// static String server = "wss://s.jingtum.com:5020";// 生产环境
	static String server = "ws://ts5.jingtum.com:5020";// 测试环境
	static Boolean local_sign = true;// 是否使用本地签名方式提交交易
	static Connection conn = ConnectionFactory.getCollection(server);
	static Remote remote = new Remote(conn, local_sign);

	public static void main(String[] args) {
		createOffer();//创建订单
//		getlOffers();//获取挂单列表
//		cancelOffer();//取消挂单
		
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
	 * 获取账号订单
	 * @param account
	 * @return
	 */
	public static AccountOffers getlOffers() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		AccountOffers bean = remote.requestAccountOffers(account, null);
		System.err.println(JsonUtils.toJsonString(bean));
		for(Offer offer : bean.getOffers()){
			System.out.println("挂单编号："+offer.getSeq());
		}
		return bean;
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

}
