package com.blink.jtblc.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blink.jtblc.client.bean.AccountInfo;
import com.blink.jtblc.client.bean.PaymentInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;
import com.blink.jtblc.core.coretypes.AccountID;
import com.blink.jtblc.core.coretypes.Amount;
import com.blink.jtblc.core.coretypes.uint.UInt32;
import com.blink.jtblc.core.types.known.tx.signed.SignedTransaction;
import com.blink.jtblc.core.types.known.tx.txns.Payment;

public class TransactionTest {
	protected static final Logger log = LoggerFactory.getLogger(RemoteTest.class);
	// 井通节点websocket链接：
	// ws://ts5.jingtum.com:5020
	String server = "ws://ts5.jingtum.com:5020";
	// 子账号
	String account_1 = "j3UcBBbes7HFgmTLmGkEQQShM2jdHbdGAe";
	String secret_1 = "ssWiEpky7Bgj5GFrexxpKexYkeuUv";
	// 主账号
	String account_2 = "jNn89aY84G23onFXupUd7bkMode6aKYMt8";
	String secret_2 = "spvFsSWaD1BmNk7h3Zvo98YRi1NxX";
	Integer limit_num = 100;
	Connection conn = ConnectionFactory.getCollection(server);
	Remote remote = new Remote(conn);
	
	@Test
	public void testCreatePaymentTransaction() throws Exception {
		try {
			byte data[] = "给jDUjqoDZLhzx4DCf6pvSivjkjgtRESY62c支付0.5swt.".getBytes("UTF-8");
			byte[] encodeData = Hex.encode(data);
			String encodeStr = Hex.toHexString(data);
			System.out.println(new String(encodeData, "UTF-8"));
			System.out.println(encodeStr);
			// 解码
			byte[] decodeData = Hex.decode(encodeData);
			byte[] decodeData2 = Hex.decode(encodeStr);
			System.out.println(new String(decodeData, "UTF-8"));
			System.out.println(new String(decodeData2, "UTF-8"));
			String value = "0.000001";
			Amount amount = new Amount(new BigDecimal(500000));
			String account = "jB7rxgh43ncbTX4WeMoeadiGMfmfqY2xLZ";
			String to = "jDUjqoDZLhzx4DCf6pvSivjkjgtRESY62c";
			String secret = "sn37nYrQ6KPJvTFmaBYokS3FjXUWd";
			AccountInfo ainfo = remote.requestAccountInfo(account, null, "trust");
			Payment payment = new Payment(remote);
			// Put `as` AccountID field Account, `Object` o
			payment.as(AccountID.Account, "jB7rxgh43ncbTX4WeMoeadiGMfmfqY2xLZ");
			payment.as(AccountID.Destination, "jDUjqoDZLhzx4DCf6pvSivjkjgtRESY62c");
			payment.as(Amount.Amount, "500000");
			payment.as(Amount.Fee, "10000");
			payment.sequence(new UInt32(ainfo.getAccountData().getSequence()));
			payment.flags(new UInt32(0));
			List<String> memos = new ArrayList<String>();
			memos.add("给jDUjqoDZLhzx4DCf6pvSivjkjgtRESY62c支付0.5swt.");
			payment.addMemo(memos);
			PaymentInfo info = payment.submit(secret);
			SignedTransaction tx = payment.sign(secret);
			System.out.println(tx.tx_blob);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
