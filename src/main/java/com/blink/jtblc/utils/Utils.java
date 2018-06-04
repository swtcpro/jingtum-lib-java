package com.blink.jtblc.utils;

import java.math.BigInteger;

import com.blink.jtblc.encoding.common.B16;

public class Utils {
	public static String bigHex(BigInteger bn) {
		return B16.toStringTrimmed(bn.toByteArray());
	}
	
	public static BigInteger uBigInt(byte[] bytes) {
		return new BigInteger(1, bytes);
	}
}
