package com.blink.jtblc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.blink.jtblc.client.Wallet;
import com.blink.jtblc.client.bean.Amount;
import com.blink.jtblc.config.Config;

public class CheckUtils {
	/**
	 * 校验钱包地址是否有效
	 * 
	 * @param address
	 * @return
	 */
	public static boolean isValidAddress(String address) {
		if (StringUtils.isNotEmpty(address)) {
			Wallet wallet = new Wallet();
			return wallet.isValidAddress(address);
		}
		return false;
	}
	
	public static final String CURRENCY_RE = "(([a-zA-Z0-9]{3,6})|([A-F0-9]{40}))";// 邮政编码的正则表达式
	public static Pattern pattern = Pattern.compile(CURRENCY_RE);
	
	/**
	 * 校验金额对象是否有效
	 * 
	 * @param amount
	 * @return
	 */
	public static boolean isValidAmount(Amount amount) {
		if (StringUtils.isBlank(amount.getCurrency()) || !pattern.matcher(amount.getCurrency()).find()) {
			return false;
		}
		if (amount.getCurrency().equals(Config.CURRENCY) && StringUtils.isNotBlank(amount.getIssuer())) {
			return false;
		}
		if (!amount.getCurrency().equals(Config.CURRENCY) && !Wallet.isValidAddress(amount.getIssuer())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验金额对象中的金额是否有效
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isValidAmountValue(String value) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * 交易交易类型是否有效
	 * 
	 * @param type 关系类型
	 * @param value 关系值
	 * @return
	 */
	public static boolean isValidType(String type, String value) {
		if (StringUtils.isEmpty(type) || StringUtils.isEmpty(value)) {
			return false;
		}
		String[] offer_types = new String[] { "Sell", "Buy" };
		String[] relation_types = new String[] { "trust", "authorize", "freeze", "unfreeze" };
		String[] accountSet_types = new String[] { "property", "delegate", "signer" };
		boolean flag = false;
		switch (type) {
			case "relation":
				if (ArrayUtils.contains(relation_types, value)) {
					flag = true;
				}
				break;
			case "offer":
				if (ArrayUtils.contains(offer_types, value)) {
					flag = true;
				}
				break;
			case "accountSet":
				if (ArrayUtils.contains(accountSet_types, value)) {
					flag = true;
				}
				break;
			default:
		}
		return flag;
	}
	
	/**
	 * 
	 * @param set_flag/clear_flag
	 * @return
	 */
	public static String prepareFlag(Number flag) {
		// ripple:TransactionFlag.java
		// node.js
		/*
		 * Transaction.set_clear_flags = {
		 * AccountSet: {
		 * asfRequireDest: 1,
		 * asfRequireAuth: 2,
		 * asfDisallowSWT: 3,
		 * asfDisableMaster: 4,
		 * asfNoFreeze: 6,
		 * asfGlobalFreeze: 7
		 * }
		 * };
		 */
		// return (flag instanceof Number) ? flag : (SetClearFlags[flag] || SetClearFlags["asf" + flag]);
		return "";
	}
	
	/**
	 * 判断string是否为数字
	 * 
	 * @param ledger_index
	 * @return
	 */
	public static boolean isNumeric(String ledger_index) {
		if (StringUtils.isNotEmpty(ledger_index)) {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(ledger_index);
			if (!isNum.matches()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断账本hash是否有效
	 * 
	 * @param ledger_hash
	 * @return
	 */
	public static boolean isValidHash(String ledger_hash) {
		if (StringUtils.isNotEmpty(ledger_hash)) {
		}
		return true;
	}
}
