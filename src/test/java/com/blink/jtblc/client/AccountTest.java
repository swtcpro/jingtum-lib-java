package com.blink.jtblc.client;

import java.util.ArrayList;
import java.util.List;

import com.blink.jtblc.client.Remote;
import com.blink.jtblc.client.Transaction;
import com.blink.jtblc.client.bean.AccountInfo;
import com.blink.jtblc.client.bean.AccountRelations;
import com.blink.jtblc.client.bean.AccountTums;
import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.Line;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;

public class AccountTest {

//	 static String server = "wss://s.jingtum.com:5020";// 生产环境
	static String server = "ws://ts5.jingtum.com:5020";// 测试环境
	static Boolean local_sign = false;// 是否使用本地签名方式提交交易
	static Connection conn = ConnectionFactory.getCollection(server);
	static Remote remote = new Remote(conn, local_sign);

	public static void main(String[] args) {
//		getAccountSwtc();
//		getAccountTum();
		getAccountRelations();
		accountRelationTx();
	}

	/**
	 * 获取账户swtc
	 * @return
	 */
	public static void getAccountSwtc() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		AccountInfo result1 = remote.requestAccountInfo(account, null,"trust");
		System.out.println("swtc："+result1.getAccountData().getBalance());
		AccountInfo result2 = remote.requestAccountInfo(account, null,"authorize");
		System.out.println("swtc："+result2.getAccountData().getBalance());
		AccountInfo result3 = remote.requestAccountInfo(account, null,"freeze");
		System.out.println("swtc："+result3.getAccountData().getBalance());
	}
	
	/**
	 * 获取账户可接收和发送的货币
	 * @return
	 */
	public static void getAccountTum() {
		String account = "jPy7cSjzvooDK6Bv2FnTM8Legzb7dzyKAb";
		AccountTums result = remote.requestAccountTums(account, null);
		System.out.println("可接收Tum："+result.getReceiveCurrencies().toString());
		System.out.println("可发送Tum："+result.getSendCurrencies().toString());
	}
	
	/**
	 * 获取账户关系（trust:账户拥有的其他通）
	 * @return
	 */
	public static void getAccountRelations() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		AccountRelations result1 = remote.requestAccountRelations(account, null,"trust");
		AccountRelations result2 = remote.requestAccountRelations(account, null,"freeze");
		AccountRelations result3 = remote.requestAccountRelations(account, null,"authorize");
		for(Line line : result1.getLines()){
			System.out.println("编码："+line.getCurrency());
			System.out.println("数量："+line.getBalance());
			System.out.println("银关："+line.getAccount());
			System.out.println("============trust====================");
		}
		for(Line line : result2.getLines()){
			System.out.println("编码："+line.getCurrency());
			System.out.println("发行方："+line.getIssuer());
			System.out.println("冻结数量："+line.getLimit());
			System.out.println("冻结指向账户："+line.getLimitPeer());
			System.out.println("=============freeze===================");
		}
		for(Line line : result3.getLines()){
			System.out.println("编码："+line.getCurrency());
			System.out.println("发行方："+line.getIssuer());
			System.out.println("冻结数量："+line.getLimit());
			System.out.println("冻结指向账户："+line.getLimitPeer());
			System.out.println("============authorize====================");
		}
		
	}
	
	/**
	 * 设置关系
	 * @return
	 */
	public static void accountRelationTx() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		String to = "jNn89aY84G23onFXupUd7bkMode6aKYMt8";
		String secret = "ssWiEpky7Bgj5GFrexxpKexYkeuUv";
		String type = "freeze";
		
//		String account = "jNn89aY84G23onFXupUd7bkMode6aKYMt8";
//		String to = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
//		String secret = "spvFsSWaD1BmNk7h3Zvo98YRi1NxX";
//		String type = "unfreeze";
		
		AmountInfo amount = new AmountInfo();
		amount.setCurrency("CNY");
		amount.setValue("8");
		amount.setIssuer("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		Transaction tx = remote.buildRelationTx(type, account, to, amount);
		tx.setSecret(secret);
		List<String> memos = new ArrayList<String>();
		memos.add(account+"===给==="+to+"==="+type+"==="+amount.getValue()+amount.getCurrency());
		tx.addMemo(memos);
		TransactionInfo bean = tx.submit();
		if ("0".equals(bean.getEngineResultCode())) {
			System.out.println("交易成功 Hash：" + bean.getTxJson().getHash());
		} else {
			System.out.println("交易失败 Message：" + bean.getEngineResult());
		}
	}

	

}
