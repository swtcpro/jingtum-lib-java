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
	
	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * 
	 * @param str
	 * @return
	 */
	public static String strToHexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			// sb.append(' ');
		}
		return sb.toString().trim();
	}
	
	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * @param hexStr
	 * @return
	 */
	public static String hexStrToStr(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    byte[] bytes = new byte[hexStr.length() / 2];
	    int n;
	    for (int i = 0; i < bytes.length; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return new String(bytes);
	}
	
	/**
	 * 处理swtc金额除以1000000.0
	 * @param amount
	 * @return
	 */
	public static String  amountFormatDivide(String amount){
		if(!"0".equals(amount)){
			BigDecimal temp = new BigDecimal(amount);
			BigDecimal exchange_rate = new BigDecimal("1000000.0");
			BigDecimal rs = temp.divide(exchange_rate);
			return String.valueOf(rs.intValue());
		}else{
			return "0";
		}
	}
	
	
}
