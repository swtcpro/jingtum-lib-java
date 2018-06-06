package com.blink.jtblc.utils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * name utility
 */
public class NameUtils {
	/**
	 * covert field name to column name userName --> user_name
	 * covert class name to column name UserName -- > user_name
	 */
	public static String getUnderlineName(String propertyName) {
		if (null == propertyName) {
			return "";
		}
		StringBuilder sbl = new StringBuilder(propertyName);
		sbl.setCharAt(0, Character.toLowerCase(sbl.charAt(0)));
		propertyName = sbl.toString();
		char[] chars = propertyName.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (CharUtils.isAsciiAlphaUpper(c)) {
				sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * covert field name to column name
	 */
	public static String getHumnName(String fieldName) {
		if (null == fieldName) {
			return "";
		}
		fieldName = fieldName.toLowerCase();
		char[] chars = fieldName.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {
					sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
					i++;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}