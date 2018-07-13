package com.blink.jtblc.core.serialized;

import java.security.MessageDigest;
import java.util.ArrayList;

public class BytesList implements BytesSink {
	private ArrayList<byte[]> buffer = new ArrayList<byte[]>();
	private int len = 0;
	
	public void add(BytesList bl) {
		for (byte[] bytes : bl.rawList()) {
			add(bytes);
		}
	}
	
	/**
	 * 增加字节
	 */
	@Override
	public void add(byte aByte) {
		add(new byte[] { aByte });
	}
	
	@Override
	public void add(byte[] bytes) {
		len += bytes.length;
		buffer.add(bytes);
	}
	
	public byte[] bytes() {
		int n = bytesLength();
		byte[] bytes = new byte[n];
		addBytes(bytes, 0);
		return bytes;
	}
	
	static public String[] hexLookup = new String[256];
	static {
		// 将0-256的16进制记录缓存
		for (int i = 0; i < 256; i++) {
			String s = Integer.toHexString(i).toUpperCase();
			if (s.length() == 1) {
				s = "0" + s;
			}
			hexLookup[i] = s;
		}
	}
	
	/**
	 * 将二进度转为16进制
	 * 
	 * @return
	 */
	public String bytesHex() {
		StringBuilder builder = new StringBuilder(len * 2);
		for (byte[] buf : buffer) {
			for (byte aBytes : buf) {
				builder.append(hexLookup[aBytes & 0xFF]);// 0xFF=255
			}
		}
		return builder.toString();
	}
	
	/**
	 * 将缓存的字节copy到byte数组
	 * 
	 * @param bytes
	 * @param destPos
	 * @return
	 */
	private int addBytes(byte[] bytes, int destPos) {
		for (byte[] buf : buffer) {
			System.arraycopy(buf, 0, bytes, destPos, buf.length);
			destPos += buf.length;
		}
		return destPos;
	}
	
	// SHA-1 or SHA-256
	public void updateDigest(MessageDigest digest) {
		for (byte[] buf : buffer) {
			digest.update(buf);
		}
	}
	
	public int bytesLength() {
		return len;
	}
	
	public ArrayList<byte[]> rawList() {// 未加工
		return buffer;
	}
}
