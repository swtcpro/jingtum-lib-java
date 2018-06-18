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
	public static String getCamelName(String fieldName) {
		if (null == fieldName) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		if (fieldName.contains("_")) {
			fieldName = fieldName.toLowerCase();
		}
		char[] chars = fieldName.toCharArray();
		if (isUpper(chars[0])) {
			chars[0] = toLower(chars[0]);
		}
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
	
	public static boolean isUpper(char c) {
		return (c >= 'A' && c <= 'Z') ? true : false;
	}
	
	public static boolean isLower(char c) {
		return (c >= 'a' && c <= 'z') ? true : false;
	}
	
	public static char toUpper(char c) {
		return (char) (c & 223);
	}
	
	public static char toLower(char c) {
		return (char) (c | 32);
	}
	
	public static void main(String[] args) {
		System.out.println(NameUtils.getCamelName("aa_bb_cc"));
		System.out.println(NameUtils.getCamelName("aaBb_cc"));
		System.out.println(NameUtils.getCamelName("AaBbCc"));
		System.out.println(NameUtils.isUpper('A'));
		System.out.println(NameUtils.isUpper('a'));
		System.out.println(NameUtils.isLower('A'));
		System.out.println(NameUtils.isLower('a'));
		System.out.println(NameUtils.toUpper('A'));
		System.out.println(NameUtils.toUpper('a'));
		System.out.println(NameUtils.toLower('A'));
		System.out.println(NameUtils.toLower('a'));
	}
}