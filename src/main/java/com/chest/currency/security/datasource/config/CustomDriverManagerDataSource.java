package com.chest.currency.security.datasource.config;

import java.util.Base64;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CustomDriverManagerDataSource extends DriverManagerDataSource {

	public CustomDriverManagerDataSource() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jdbc.datasource.AbstractDriverBasedDataSource#setUsername
	 * (java.lang.String)
	 */
	public synchronized void setUsername(String encryptedUsername) {
		super.setUsername(base64Decode(encryptedUsername));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jdbc.datasource.AbstractDriverBasedDataSource#setPassword
	 * (java.lang.String)
	 */
	public synchronized void setPassword(String encryptedPassword) {
		super.setPassword(base64Decode(encryptedPassword));
	}

	/**
	 * @param token
	 * @return encoded
	 */
	public static String base64Encode(String token) {
		return Base64.getEncoder().encodeToString(token.getBytes());
	}

	/**
	 * @param token
	 * @return
	 */
	public static String base64Decode(String enryptedToken) {
		byte[] decodedBytes = Base64.getDecoder().decode(enryptedToken);
		return new String(decodedBytes);
	}

//Utility to encrypt db userid/pass
	public static void main(String args[]) {

		System.out.println("Encrypted username :- " + base64Encode("casavup"));
		System.out.println("Encrypted password :- " + base64Encode("casavup_123"));

	}
}

