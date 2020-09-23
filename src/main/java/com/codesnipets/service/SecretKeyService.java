package com.codesnipets.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 
 * @author memon
 * Secret Key generator class
 */

public class SecretKeyService {
	private static KeyGenerator keygenerator;
	
	public static SecretKey generate() 
			throws NoSuchAlgorithmException {
		keygenerator = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = new SecureRandom();
		int keyBitSize = 256;
		keygenerator.init(keyBitSize, secureRandom);
		SecretKey secret = keygenerator.generateKey();
		return secret;
	}
	
}
