package com.blink.jtblc.encoding.common;

import static org.bouncycastle.util.encoders.Hex.toHexString;

public class B16 {
	public static String toStringTrimmed(byte[] bytes) {
		int offset = 0;
		if (bytes[0] == 0) {
			offset = 1;
		}
		return toHexString(bytes, offset, bytes.length - offset).toUpperCase();
	}

	@Deprecated
	public static String toString(byte[] bytes) {
		return encode(bytes);
	}

	public static String encode(byte[] bytes) {
		return toHexString(bytes).toUpperCase();
	}

	public static byte[] decode(String hex) {
		return decode(hex);
	}
}
