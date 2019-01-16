package com.chest.currency.security.datasource.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class AESEncryptionDecryption extends DriverManagerDataSource {

	public AESEncryptionDecryption() {
		super();
	}

	private static SecretKeySpec secretKey;
	private static byte[] key;

	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public synchronized void setUsername(String encryptedUsername) {
		super.setUsername(AESEncryptionDecryption.decrypt(encryptedUsername, "ssshhhhhhhhhhh!!!!"));
	}

	public synchronized void setPassword(String encryptedPassword) {
		super.setPassword(AESEncryptionDecryption.decrypt(encryptedPassword, "ssshhhhhhhhhhh!!!!"));
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		/*String encryptedUsername = AESEncryptionDecryption.encrypt("icmc_user", "ssshhhhhhhhhhh!!!!");
		String encryptedPassword = AESEncryptionDecryption.encrypt("Icici@2018#", "ssshhhhhhhhhhh!!!!");
		String decryptedusername = AESEncryptionDecryption.decrypt(encryptedUsername, "ssshhhhhhhhhhh!!!!");
		String decryptedPassword = AESEncryptionDecryption.decrypt(encryptedPassword, "ssshhhhhhhhhhh!!!!");*/
		
		/*System.out.println("Encrypted username :- " + encryptedUsername);
		System.out.println("Encrypted password :- " + encryptedPassword);
		System.out.println("decryptedusername username :- " + decryptedusername);
		System.out.println("decryptedPassword password :- " + decryptedPassword);*/
		System.out.println("Encrypted username :- ");
	}

}
