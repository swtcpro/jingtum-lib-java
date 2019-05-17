package com.blink.jtblc.client;

import java.util.ArrayList;
import java.util.List;

import com.blink.jtblc.client.Remote;
import com.blink.jtblc.client.Transaction;
import com.blink.jtblc.client.Wallet;
import com.blink.jtblc.client.bean.Account;
import com.blink.jtblc.client.bean.AccountData;
import com.blink.jtblc.client.bean.AccountInfo;
import com.blink.jtblc.client.bean.AccountTx;
import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.client.bean.Memo;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.client.bean.Transactions;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
/**
 * 交易相关测试
 * @author yyl
 * @date 2019年5月17日
 */
public class TransactionTest {

	// static String server = "wss://s.jingtum.com:5020";// 生产环境
	static String server = "ws://ts5.jingtum.com:5020";// 测试环境
	static Boolean local_sign = true;// 是否使用本地签名方式提交交易
	static Connection conn = ConnectionFactory.getCollection(server);
	static Remote remote = new Remote(conn, local_sign);

	public static void main(String[] args) {
		// 创建钱包
		Wallet wallet = Wallet.generate();
		System.out.println("address:" + wallet.getAddress());
		System.out.println("secret:" + wallet.getSecret());

		// 交易上链
		sendTransaction();

		// 获取交易信息
		getTx();

		// 获取SWTC余额
		getSwtcBleans();

		// 获取账号交易列表
		getTxs();
	}

	/**
	 * 数据上链
	 * 
	 * @param memo
	 * @return
	 */
	public static String sendTransaction() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		String to = "jNn89aY84G23onFXupUd7bkMode6aKYMt8";
		String secret = "ssWiEpky7Bgj5GFrexxpKexYkeuUv";
		AmountInfo amount = new AmountInfo();
		// amount.setCurrency("CNY");
		amount.setCurrency("SWT");
		amount.setValue("0.01");
		// amount.setIssuer("jBciDE8Q3uJjf111VeiUNM775AMKHEbBLS");
		Transaction tx = remote.buildPaymentTx(account, to, amount);
		tx.setSecret(secret);
		List<String> memos = new ArrayList<String>();
		memos.add("测试数据111");
		memos.add("测试数据222");
		memos.add("测试数据333");
		tx.addMemo(memos);
		TransactionInfo bean = tx.submit();
		if ("0".equals(bean.getEngineResultCode())) {
			System.out.println("数据上链成功 Hash：" + bean.getTxJson().getHash());
			return bean.getTxJson().getHash();
		} else {
			System.out.println("数据上链失败 Message：" + bean.getEngineResult());
			return bean.getEngineResult();
		}
	}

	/**
	 * 根据hash获取交易信息
	 * 
	 * @param hash
	 * @return
	 */
	public static Account getTx() {
		String hash = "77D66074F56B76618DF30B04DCDB12E0A5E8D3B895404402E46C737E7EB194BD";
		Account bean = remote.requestTx(hash);
		System.out.println("from:" + bean.getAccount());
		System.out.println("to:" + bean.getDestination());
		if (bean.getAmount() != null) {
			System.out.println("currency:" + bean.getAmount().getCurrency());
			System.out.println("value:" + bean.getAmount().getValue());
			System.out.println("issuer:" + bean.getAmount().getIssuer());
		}
		if (bean.getMemos() != null) {
			for (Memo memo : bean.getMemos()) {
				System.out.println("memo:" + memo.getMemoData());
			}
		}
		return bean;
	}

	/**
	 * 获取账号SWTC余额信息
	 * 
	 * @return
	 */
	public static AccountData getSwtcBleans() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		AccountInfo bean = remote.requestAccountInfo(account, null, null);
		System.out.println("j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe---SWTC余额：" + bean.getAccountData().getBalance());
		return bean.getAccountData();
	}

	/**
	 * 获取交易记录列表
	 * 
	 * @return
	 */
	public static void getTxs() {
		String account = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
		AccountTx accountTx = remote.requestAccountTx(account, 5, null);// 前五条数据
		AccountTx accountTx2 = remote.requestAccountTx(account, 5, accountTx.getMarker());// 后五条数据
		for (Transactions a : accountTx.getTransactions()) {
			System.out.println("date:" + a.getDate() + "======hash:" + a.getHash());
			if (a.getMemos() != null) {
				for (Memo memo : a.getMemos()) {
					System.out.println(memo.getMemoData());
				}
			}
			System.out.println(a.getEffects().toString());
		}
		System.out.println("------------------");
		for (Transactions a : accountTx2.getTransactions()) {
			System.out.println(a.getDate());
			if (a.getMemos() != null) {
				for (Memo memo : a.getMemos()) {
					System.out.println(memo.getMemoData());
				}
			}
			System.out.println(a.getEffects().toString());
		}
	}

}
