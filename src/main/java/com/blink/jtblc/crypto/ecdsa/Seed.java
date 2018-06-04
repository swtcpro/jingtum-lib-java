package com.blink.jtblc.crypto.ecdsa;

import static com.blink.jtblc.config.Config.getB58IdentiferCodecs;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import com.blink.jtblc.config.Config;
import com.blink.jtblc.encoding.B58IdentiferCodecs;
import com.blink.jtblc.encoding.base58.B58;
import com.blink.jtblc.utils.Sha512;
import com.blink.jtblc.utils.Utils;

public class Seed {
	public static byte[] VER_K256 = new byte[] { (byte) B58IdentiferCodecs.VER_FAMILY_SEED };
	public static byte[] VER_ED25519 = new byte[] { (byte) 0x1, (byte) 0xe1, (byte) 0x4b };
	final byte[] seedBytes;
	byte[] version;

	public Seed(byte[] seedBytes) {
		this(VER_K256, seedBytes);
	}

	public Seed(byte[] version, byte[] seedBytes) {
		this.seedBytes = seedBytes;
		this.version = version;
	}

	@Override
	public String toString() {
		return Config.getB58().encodeToStringChecked(seedBytes, version);
	}

	public byte[] bytes() {
		return seedBytes;
	}

	public byte[] version() {
		return version;
	}

	public Seed setEd25519() {
		this.version = VER_ED25519;
		return this;
	}

	public IKeyPair keyPair() {
		return keyPair(0);
	}

	public IKeyPair rootKeyPair() {
		return keyPair(-1);
	}

	public IKeyPair keyPair(int account) {
		if (Arrays.equals(version, VER_ED25519)) {
			if (account != 0) {
				throw new AssertionError();
			}
			return EDKeyPair.from128Seed(seedBytes);
		} else {
			return createKeyPair(seedBytes, account);
		}
	}

	public static Seed fromBase58(String b58) {
		B58.Decoded decoded = Config.getB58().decodeMulti(b58, 16, VER_K256, VER_ED25519);
		return new Seed(decoded.version, decoded.payload);
	}

	public static Seed fromPassPhrase(String passPhrase) {
		return new Seed(passPhraseToSeedBytes(passPhrase));
	}

	public static byte[] passPhraseToSeedBytes(String phrase) {
		try {
			return new Sha512(phrase.getBytes("utf-8")).finish128();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static IKeyPair createKeyPair(byte[] seedBytes) {
		return createKeyPair(seedBytes, 0);
	}

	public static IKeyPair createKeyPair(byte[] seedBytes, int accountNumber) {// accountNumber=0
		BigInteger secret, pub, privateGen;
		// The private generator (aka root private key, master private key)
		privateGen = K256KeyPair.computePrivateGen(seedBytes);// li-ok
		byte[] publicGenBytes = K256KeyPair.computePublicGenerator(privateGen);// li-ok
		if (accountNumber == -1) {
			// The root keyPair
			return new K256KeyPair(privateGen, Utils.uBigInt(publicGenBytes));
		} else {
			secret = K256KeyPair.computeSecretKey(privateGen, publicGenBytes, accountNumber);// li-ok
			pub = K256KeyPair.computePublicKey(secret);
			return new K256KeyPair(secret, pub);
		}
	}

	public static IKeyPair getKeyPair(byte[] seedBytes) {
		return createKeyPair(seedBytes, 0);
	}

	public static IKeyPair getKeyPair(String b58) {
		return getKeyPair(getB58IdentiferCodecs().decodeFamilySeed(b58));
	}

	public static String random() {
		byte[] randBytes = new byte[16];
		Random random = new Random();
		random.nextBytes(randBytes);
		byte[] bytes = new byte[randBytes.length + 1];
		bytes[0] = (byte) Config.SEED_PREFIX;
		System.arraycopy(randBytes, 0, bytes, 1, randBytes.length);
		byte[] sha256 = null;
		try {
			sha256 = MessageDigest.getInstance("SHA-256").digest(bytes);
			sha256 = MessageDigest.getInstance("SHA-256").digest(sha256);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] checksum = Arrays.copyOf(sha256, 4);
		byte[] ret = new byte[bytes.length + checksum.length];
		System.arraycopy(bytes, 0, ret, 0, bytes.length);
		System.arraycopy(checksum, 0, ret, bytes.length, checksum.length);
		return Config.getB58().encodeToString(ret);
	}
}
