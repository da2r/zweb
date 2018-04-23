package com.cpssoft.dev.zweb.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashUtil {

	public static String md5(String s) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(), 0, s.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static String hmacsha256(String raw, String key) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);

			byte[] rawHmac = mac.doFinal(raw.getBytes());

			return toHex(rawHmac);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static String toHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

}
