package com.beatus.billlive.encryption;

import javax.crypto.spec.SecretKeySpec;

import com.beatus.billlive.exception.BillliveServiceException;

/**
 * 
 * @author vakey
 *
 */
public class HmacKeyGenerator extends KeyGenerator{
	
	/*private static final byte[] SALT = { 
		(byte) 0x1C, (byte) 0x33, (byte) 0x18, (byte) 0x63, 
		(byte) 0xC8, (byte) 0xA4, (byte) 0x3F, (byte) 0xD2,
		(byte) 0x30, (byte) 0x08, (byte) 0x0F, (byte) 0xC7, 
		(byte) 0xA4, (byte) 0xB0, (byte) 0x48, (byte) 0x26 };
	
	private static final String KEYGEN_ALGORITHM = "PBKDF2WithHmacSHA1";
	
	private static final int ITERATION_COUNT = 1000;
	
	private static final int KEY_LENGTH = 160;*/
	
	public static final String HMAC_KEY_ID = "BL-SC-Hmac-Cookie-Key";
	
	@Override
	public SecretKeySpec generateKey(String algorithm) throws BillliveServiceException {
		return generateKey(HMAC_KEY_ID, algorithm);
	}
	
	@Override
	public SecretKeySpec generateKey(String keyId, String algorithm) throws BillliveServiceException {
		return super.generateKey(keyId, algorithm);

	}

}
