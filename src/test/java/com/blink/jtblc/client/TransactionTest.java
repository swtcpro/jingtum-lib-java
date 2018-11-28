package com.blink.jtblc.client;

import java.math.BigDecimal;

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
	String server = "ws://101.200.176.238:5020";
	// 子账号
	String account_1 = "jK4kdiriyxErTfW8wMMjzP25oT2AKLWGfY";
	String secret_1 = "shrh9UUHLfDfEiByzakXu6Qorf2T7";
	// 主账号
	String account_2 = "j47gDd3ethDU4UJMD2rosg9WrSXeh9bLd1";
	String secret_2 = "shFsfC6b1GCfxHn4Y4b3TSALHo37i";
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
			payment.addMemo("给jDUjqoDZLhzx4DCf6pvSivjkjgtRESY62c支付0.5swt.");
			PaymentInfo info = payment.submit(secret);
			SignedTransaction tx = payment.sign(secret);
			System.out.println(tx.tx_blob);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
