package com.beatus.billlive.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beatus.billlive.encryption.EncryptionKeyGenerator;
import com.beatus.billlive.encryption.HmacKeyGenerator;
import com.beatus.billlive.encryption.KeyGenerator;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.google.api.services.cloudkms.v1.CloudKMS;
import com.google.api.services.cloudkms.v1.model.DecryptRequest;
import com.google.api.services.cloudkms.v1.model.DecryptResponse;
import com.google.api.services.cloudkms.v1.model.EncryptRequest;
import com.google.api.services.cloudkms.v1.model.EncryptResponse;

@Component("googleCloudKMS")
public class GoogleCloudKMS {

	@Autowired
	@Qualifier(value = "cloudKMS")
	private CloudKMS cloudKMS;

	public static final String PROJECT_ID = "billlive-webapp";
	public static final String LOCATION = "asia-east1";
	public static final String RING_ID = "cookie-key";
	public static final String ENCRYPTION_KEY_ID = "BL-SC-Enc-Cookie-Key";
	public static final String HMAC_KEY_ID = "BL-SC-Hmac-Cookie-Key";
	public static final String VERSION = "1";

	private static Map<String, KeyGenerator> getKeyBasedOnAlgorithm = new HashMap<String, KeyGenerator>();
	
	static {
			getKeyBasedOnAlgorithm.put(Constants.AES, new EncryptionKeyGenerator());
			getKeyBasedOnAlgorithm.put(Constants.HMACSHA256, new HmacKeyGenerator());
	}
	
	public SecretKeySpec getKey(String algorithm) throws BillliveServiceException{
		return getKeyBasedOnAlgorithm.get(algorithm).generateKey(algorithm);
	}

	/**
	 * Encrypts the given bytes, using the primary version of the specified
	 * crypto key.
	 *
	 * The primary version can be updated via the <a href=
	 * "https://g.co/cloud/cloudKMS/docs/reference/rest/v1/projects.locations.keyRings.cryptoKeys/updatePrimaryVersion">
	 * updatePrimaryVersion</a> method.
	 */
	public byte[] encrypt(byte[] plaintext) throws IOException {
		// The resource name of the cryptoKey
		String cryptoKeyName = String.format("projects/%s/locations/%s/keyRings/%s/cryptoKeys/%s", PROJECT_ID, LOCATION,
				RING_ID, ENCRYPTION_KEY_ID);
		if (null != VERSION) {
			cryptoKeyName += "/cryptoKeyVersions/" + VERSION;
		}

		EncryptRequest request = new EncryptRequest().encodePlaintext(plaintext);
		EncryptResponse response = cloudKMS.projects().locations().keyRings().cryptoKeys()
				.encrypt(cryptoKeyName, request).execute();

		return response.decodeCiphertext();
	}
	// [END kms_encrypt]

	// [START kms_decrypt]
	/**
	 * Decrypts the given encrypted bytes, using the specified crypto key.
	 */
	public byte[] decrypt(byte[] encrypted) throws IOException {

		// The resource name of the cryptoKey
		String cryptoKeyName = String.format("projects/%s/locations/%s/keyRings/%s/cryptoKeys/%s", PROJECT_ID, LOCATION,
				RING_ID, ENCRYPTION_KEY_ID);

		if (null != VERSION) {
			cryptoKeyName += "/cryptoKeyVersions/" + VERSION;
		}

		DecryptRequest request = new DecryptRequest().encodeCiphertext(encrypted);
		DecryptResponse response = cloudKMS.projects().locations().keyRings().cryptoKeys()
				.decrypt(cryptoKeyName, request).execute();

		return response.decodePlaintext();
	}
	// [END kms_decrypt]

}
