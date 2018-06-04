package com.blink.jtblc.wallet;

import java.math.BigInteger;
import java.util.Arrays;

import com.blink.jtblc.client.Wallet;

public class test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = " 123 ";
		byte[] bits = str.getBytes();
		int[] inta = new int[] { 190, 145, 99, 160, 58, 70, 214, 178, 128, 91, 192, 20, 127, 223, 238, 94, 178, 221, 254, 146, 233, 57, 218,
		        75, 148, 178, 170, 253, 181, 66, 118, 177 };
		;
		byte[] nbytes = new byte[inta.length];
		int ind = 0;
		for (int i : inta) {
			nbytes[ind++] = (byte) i;
		}
		BigInteger bigInteger = new BigInteger(nbytes);
		System.out.println("bigInteger=" + bigInteger.toString(16));
		bits = Arrays.copyOfRange(bits, 0, bits.length - 1);
		for (byte bit : bits) {
			System.out.println(bit);
		}
		Wallet w = new Wallet();
		w.generate();
	}
}
