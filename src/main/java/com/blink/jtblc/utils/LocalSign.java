package com.blink.jtblc.utils;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.config.Config;
import com.blink.jtblc.core.coretypes.AccountID;
import com.blink.jtblc.core.coretypes.Amount;
import com.blink.jtblc.core.coretypes.Blob;
import com.blink.jtblc.core.coretypes.Currency;
import com.blink.jtblc.core.coretypes.uint.UInt32;
import com.blink.jtblc.core.runtime.Value;
import com.blink.jtblc.core.types.known.tx.signed.SignedTransaction;
import com.blink.jtblc.core.types.known.tx.txns.Payment;
import com.blink.jtblc.crypto.ecdsa.IKeyPair;
import com.blink.jtblc.crypto.ecdsa.Seed;
import com.blink.jtblc.exceptions.RemoteException;

public class LocalSign {

	/**
	 * 本地签名获取tx_blob
	 * @param account 交易账号
	 * @param to 接受账号
	 * @param amountInfo 金额
	 * @param memos 备注
	 * @param secret 交易账号私钥
	 * @param sequence 序列 通过 AccountInfo ainfo = remote.requestAccountInfo(account, null, "trust");获取
	 * 					   sequence = ainfo.getAccountData().getSequence();
	 * @return
	 */
	public static String sign(String account, String to, AmountInfo amountInfo, List<String> memos, String secret,
			Integer sequence) {
		Payment payment = new Payment();
		payment.as(AccountID.Account, account);
		payment.as(AccountID.Destination, to);
		Object value = toAmount(amountInfo);
		if (Value.typeOf(value) == Value.STRING) {// 基础货币 swt
			payment.as(Amount.Amount, value);
		} else {// 非基础货币
			BigDecimal jine = new BigDecimal(amountInfo.getValue());
			Amount amount = new Amount(jine, Currency.fromString(amountInfo.getCurrency()),
					AccountID.fromString(amountInfo.getIssuer()));
			payment.as(Amount.Amount, amount);
		}
		payment.as(Amount.Fee, String.valueOf(Config.FEE));
		payment.sequence(new UInt32(sequence));
		payment.flags(new UInt32(0));
		payment.addMemo(memos);
		SignedTransaction tx = payment.sign(secret);
		String tx_blob = tx.tx_blob;
		return tx_blob;
	}
	
	/**
	 * 根据金额对象内容返回信息 货币单位SWT转基本单位
	 * 
	 * @param amount
	 *            金额对象
	 * @return
	 */
	public static Object toAmount(AmountInfo amount) {
		String value = amount.getValue();
		BigDecimal temp = new BigDecimal(value);
		BigDecimal max_value = new BigDecimal("100000000000");
		if (StringUtils.isNotEmpty(value) && temp.compareTo(max_value) > 0) {
			throw new RemoteException("invalid amount: amount's maximum value is 100000000000");
		}
		Boolean isNative = amount.getIsNative();
		String currency = amount.getCurrency();
		if (isNative || currency.equals(Config.CURRENCY)) {
			BigDecimal exchange_rate = new BigDecimal("1000000.00");
			BigDecimal rs = temp.multiply(exchange_rate);
			return String.valueOf(rs.intValue());
		}
		return amount;
	}
	
	public static void main(String[] args) {
		IKeyPair keyPair = Seed.fromBase58("ssWiEpky7Bgj5GFrexxpKexYkeuUv").keyPair();
		Blob pubKey = new Blob(keyPair.canonicalPubBytes());
		System.out.println(pubKey.toJSON().toString());
	}
	
}
