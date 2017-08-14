package com.beatus.billlive.encryption;

import java.io.IOException;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.beatus.billlive.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.google.api.services.cloudkms.v1.CloudKMS;
import com.google.api.services.cloudkms.v1.CloudKMS.Projects.Locations.KeyRings.CryptoKeys.Get;

/**
 * 
 * @author vakey
 *
 */
public abstract class KeyGenerator{
	
	public static final String PROJECT_ID = "billlive-webapp";
	public static final String LOCATION = "asia-east1";
	public static final String RING_ID = "cookie-key";
	public static final String VERSION = "1";
	
	@Autowired
	@Qualifier(value = "cloudKMS")
	private static CloudKMS cloudKMS;
	
	
	public SecretKeySpec generateKey(String keyId, String algorithm) throws BillliveServiceException {
		
		try{
			// The resource name of the cryptoKey
			String cryptoKeyName = String.format("projects/%s/locations/%s/keyRings/%s/cryptoKeys/%s", PROJECT_ID, LOCATION,
					RING_ID, keyId);
			if (null != VERSION) {
				cryptoKeyName += "/cryptoKeyVersions/" + VERSION;
			}
	
			Get keyResp = cloudKMS.projects().locations().keyRings().cryptoKeys().get(cryptoKeyName);
			String key = keyResp.getKey();
			if(StringUtils.isNotBlank(key)){
				SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(Constants.CHAR_SET), algorithm);
				return secretKey;
			}
		}catch(IOException e) {
			throw new BillliveServiceException(e.getMessage());
		}
		return null;
		
	}


	public abstract SecretKeySpec generateKey(String algorithm) throws BillliveServiceException;

}
