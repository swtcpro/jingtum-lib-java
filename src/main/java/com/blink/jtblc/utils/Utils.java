package com.blink.jtblc.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.blink.jtblc.client.bean.AmountInfo;
import com.blink.jtblc.encoding.common.B16;

public class Utils {
	public static void main(String[] args) {
		String aaString = Utils.hexToString("11");
		//System.out.println(aaString);
	}
	
	public static String bigHex(BigInteger bn) {
		return B16.toStringTrimmed(bn.toByteArray());
	}
	
	public static BigInteger uBigInt(byte[] bytes) {
		return new BigInteger(1, bytes);
	}
	
	public static String hexToString(String str) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		if (str.length() % 2 == 0) {
			list.add(unicode2String(String.valueOf(Integer.parseInt(str.substring(0, 1), 16))));
			i = 1;
		}
		for (; i < str.length(); i += 2) {
			list.add(unicode2String(String.valueOf(Integer.parseInt(str.substring(i, i + 2), 16))));
		}
		return String.join("", list);
	}
	
	public static String unicode2String(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);
			// 追加成string
			string.append((char) data);
		}
		return string.toString();
	}
	
	public static AmountInfo parseAmount(Object amount) {
		AmountInfo info = new AmountInfo();
	    if (amount instanceof String && StringUtils.isNotEmpty((String)amount)) {
	        String value = (new BigDecimal((String)amount)).divide(new BigDecimal(1000000.0)).toString();
	        info.setValue(value);
	    } else if (CheckUtils.isValidAmount((AmountInfo) amount)) {
	        return (AmountInfo) amount;
	    } else {
	    	info = null;
	    }
	    return info;
	}
}
