package com.example.demo;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Encryption {

	private static final Logger logger = LoggerFactory.getLogger(Encryption.class);

	public static String ALGORITHM = "AES";

	public static String generateKey(int n) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(n);
		SecretKey originalKey = keyGenerator.generateKey();
		byte[] rawData = originalKey.getEncoded();
		String encodedKey = Base64.getEncoder().encodeToString(rawData);
		return encodedKey;
	}

	public static String encrypt(final String secret, final String data) {
		byte[] decodedKey = Base64.getDecoder().decode(secret);
		try {
			Cipher cipher = Cipher.getInstance("AES");
			SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, originalKey);
			byte[] cipherText = cipher.doFinal(data.toString().getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(cipherText);
		} catch (Exception e) {
			logger.info("Exception occured while encryption..." + e.getMessage());
			throw new RuntimeException("Error occured while encrypting data", e);
		}
	}

	public static String decrypt(String secret, String encryptedString) {
		byte[] decodedKey = Base64.getDecoder().decode(secret);
		try {
			Cipher cipher = Cipher.getInstance("AES");
			SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
			cipher.init(Cipher.DECRYPT_MODE, originalKey);
			byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
			return new String(cipherText);
		} catch (Exception e) {
			logger.info("Exception occured while decryption..." + e.getMessage());
			throw new RuntimeException("Error occured while decrypting data", e);
		}
	}

}