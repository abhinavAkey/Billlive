package com.beatus.billlive.encryption;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.google.api.services.cloudkms.v1.CloudKMS;
import com.google.api.services.cloudkms.v1.CloudKMS.Projects.Locations.KeyRings.CryptoKeys.Get;
import com.google.api.services.cloudkms.v1.model.CryptoKey;
import com.google.api.services.cloudkms.v1.model.CryptoKeyVersion;
import com.google.api.services.cloudkms.v1.model.EncryptRequest;
import com.google.api.services.cloudkms.v1.model.EncryptResponse;

/**
 * 
 * @author vakey
 *
 */
@Component
public abstract class KeyGenerator {

	public static final String PROJECT_ID = "billlive-webapp";
	public static final String LOCATION = "asia-east1";
	public static final String RING_ID = "cookie-key";
	public static final String VERSION = "1";

	public SecretKeySpec generateKey(String keyId, String algorithm, CloudKMS cloudKMS)
			throws BillliveServiceException {

		try {
			// The resource name of the cryptoKey
			String cryptoKeyName = String.format("projects/%s/locations/%s/keyRings/%s/cryptoKeys/%s", PROJECT_ID,
					LOCATION, RING_ID, keyId);

			Get keyResp = null;
			com.google.api.services.cloudkms.v1.CloudKMS.Projects.Locations.KeyRings.CryptoKeys.CryptoKeyVersions.Get keyVersionResp = null;
			if (null != VERSION) {
				cryptoKeyName += "/cryptoKeyVersions/" + VERSION;
				keyVersionResp = cloudKMS.projects().locations().keyRings().cryptoKeys().cryptoKeyVersions()
						.get(cryptoKeyName);
			} else {
				keyResp = cloudKMS.projects().locations().keyRings().cryptoKeys().get(cryptoKeyName);
			}
			Entry<String, Object> keyEntry = null;
			String key = null;
			if (keyVersionResp != null) {
				CryptoKeyVersion keyVersion = keyVersionResp.execute();
				Set<String> keySet = keyVersion.keySet();
				Iterator<String> keyIterator = keySet.iterator();
				while (keyIterator.hasNext()) {
					key = keyIterator.next();
				}
			}
			if (keyResp != null) {
				CryptoKey keyVersion = keyResp.execute();
				Set<Entry<String, Object>> keySet = keyVersion.entrySet();
				Iterator<Entry<String, Object>> keyIterator = keySet.iterator();
				while (keyIterator.hasNext()) {
					keyEntry = keyIterator.next();
					if (keyId.equalsIgnoreCase(keyEntry.getKey())) {
						key = (String) keyEntry.getValue();
					}
				}
			}

			EncryptRequest request = new EncryptRequest().encodePlaintext(new String("This is the salt").getBytes());
			EncryptResponse response = cloudKMS.projects().locations().keyRings().cryptoKeys()
					.encrypt(cryptoKeyName, request).execute();
			response.decodeCiphertext();

			if (StringUtils.isNotBlank(key)) {
				SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(Constants.CHAR_SET), algorithm);
				return secretKey;
			}
		} catch (IOException e) {
			throw new BillliveServiceException(e.getMessage());
		}
		return null;

	}

	public SecretKey generateKey(String keyGenAlgorithm, char[] password, byte[] salt, int iterationCount,
			int keyLength) {

		PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);

		SecretKeyFactory secretKeyFactory;
		SecretKey secretKey;
		try {
			secretKeyFactory = SecretKeyFactory.getInstance(keyGenAlgorithm);

			secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

		} catch (NoSuchAlgorithmException e) {
			throw new BillliveServiceException("No such Algorithm Error", e);
		} catch (InvalidKeySpecException e) {
			throw new BillliveServiceException("Invalid Key Specification Error", e);
		} finally {
			pbeKeySpec.clearPassword();
		}

		return secretKey;
	}

	public abstract SecretKeySpec generateKey(String algorithm, CloudKMS cloudKMS) throws BillliveServiceException;

	public abstract SecretKey generateKey(char[] password);

}
