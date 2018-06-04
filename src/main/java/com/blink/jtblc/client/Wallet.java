package com.blink.jtblc.client;

import static com.blink.jtblc.config.Config.getB58IdentiferCodecs;

import com.blink.jtblc.config.Config;
import com.blink.jtblc.crypto.ecdsa.IKeyPair;
import com.blink.jtblc.crypto.ecdsa.Seed;
import com.blink.jtblc.encoding.B58IdentiferCodecs;
import com.blink.jtblc.encoding.common.B16;

public class Wallet {
	private IKeyPair keypairs = null;
	private String secret = null;
	
	public Wallet() {
	}
	
	public Wallet(String secret) {
		this.keypairs = Seed.fromBase58(secret).keyPair();
		this.secret = secret;
	}
	
	/**
	 * 随机生成钱包地址
	 *
	 * @return
	 */
	public static Wallet generate() {
		String secret = Seed.random();
		IKeyPair keypairs = Seed.fromBase58(secret).keyPair();
		byte[] bytes = keypairs.pub160Hash();
		String address = Config.getB58IdentiferCodecs().encodeAddress(bytes);
		// var address = Keypairs.deriveAddress(keypair.publicKey);
		// System.out.println("secret=" + secret);
		// System.out.println("address=" + address);
		Wallet wallet = new Wallet();
		wallet.setKeypairs(keypairs);
		wallet.setSecret(secret);
		return wallet;
	}
	
	/**
	 * 根据密钥生成钱包
	 * 
	 * @param secret
	 * @return
	 */
	public static Wallet fromSecret(String secret) {
		IKeyPair keypairs = Seed.fromBase58(secret).keyPair();
		Wallet wallet = new Wallet();
		wallet.setKeypairs(keypairs);
		wallet.setSecret(secret);
		return wallet;
	}
	
	/**
	 * 使用钱包密钥对信息进行签名
	 * 
	 * @param secret
	 * @return
	 */
	public String sign(String message) {
		byte[] der = this.keypairs.signMessage(message.getBytes());
		return B16.toString(der);
	}
	
	/**
	 * 校验信息的自作签名是否正确
	 * 
	 * @param message
	 * @param signature
	 * @return
	 */
	public boolean verify(String message, String signature) {
		// (byte[] hash, byte[] sigBytes
		return this.keypairs.verifySignature(message.getBytes(), signature.getBytes());
	}
	
	/**
	 * 获取公钥地址
	 * 
	 * @return
	 */
	public String getAddress() {
		byte[] bytes = this.keypairs.pub160Hash();
		return encodeAddress(bytes);
	}
	
	/**
	 * 工具方法：公钥编码
	 * 
	 * @return
	 */
	private static String encodeAddress(byte[] a) {
		return getB58IdentiferCodecs().encodeAddress(a);
	}
	
	public static boolean isValidAddress(String address) {
		try {
			getB58IdentiferCodecs().decode(address, B58IdentiferCodecs.VER_ACCOUNT_ID);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isValidSecret(String secret) {
		try {
			Seed.fromBase58(secret).keyPair();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getSecret() {
		return this.secret;
	}
	
	public IKeyPair getKeypairs() {
		return keypairs;
	}
	
	public void setKeypairs(IKeyPair keypairs) {
		this.keypairs = keypairs;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
}