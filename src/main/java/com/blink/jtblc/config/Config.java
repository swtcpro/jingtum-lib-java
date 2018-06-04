package com.blink.jtblc.config;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.blink.jtblc.encoding.B58IdentiferCodecs;
import com.blink.jtblc.encoding.base58.B58;

// Somewhat of a global registry, dependency injection ala guice would be nicer, but trying to KISS
public class Config {
	public static final String DEFAULT_ALPHABET = "jpshnaf39wBUDNEGHJKLM4PQRST7VWXYZ2bcdeCg65rkm8oFqi1tuvAxyz";
	private static B58IdentiferCodecs b58IdentiferCodecs;
	private static double feeCushion;
	private static B58 b58;
	public static int SEED_PREFIX = 33;
	
	public static void setAlphabet(String alphabet) {
		b58 = new B58(alphabet);
		b58IdentiferCodecs = new B58IdentiferCodecs(b58);
	}
	
	/**
	 * @return the configured B58IdentiferCodecs object
	 */
	public static B58IdentiferCodecs getB58IdentiferCodecs() {
		return b58IdentiferCodecs;
	}
	
	/**
	 * @return the configured B58 object
	 */
	public static B58 getB58() {
		return b58;
	}
	
	/**
	 * TODO, this is gross
	 */
	static public boolean bouncyInitiated = false;
	
	static public void initBouncy() {
		if (!bouncyInitiated) {
			Security.addProvider(new BouncyCastleProvider());
			bouncyInitiated = true;
		}
	}
	
	/***
	 * We set up all the defaults here
	 */
	static {
		setAlphabet(DEFAULT_ALPHABET);
		setFeeCushion(1.1);
		initBouncy();
	}
	
	public static double getFeeCushion() {
		return feeCushion;
	}
	
	public static void setFeeCushion(double fee_cushion) {
		feeCushion = fee_cushion;
	}
}
