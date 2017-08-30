package com.beatus.billlive.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.eclipse.osgi.internal.signedcontent.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beatus.billlive.encryption.EncryptionFactory;
import com.beatus.billlive.encryption.KeyChain;
import com.beatus.billlive.encryption.KeyChainEntries;
import com.beatus.billlive.exception.CryptoException;
import com.beatus.billlive.service.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.cloudkms.v1.CloudKMS;
import com.google.api.services.cloudkms.v1.CloudKMSScopes;
import com.google.api.services.cloudkms.v1.model.DecryptRequest;
import com.google.api.services.cloudkms.v1.model.DecryptResponse;
import com.google.api.services.cloudkms.v1.model.EncryptRequest;
import com.google.api.services.cloudkms.v1.model.EncryptResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Spring-managed REST beans. This class creates Spring-managed beans for most
 * of the classes that the REST framework needs. This allows Spring to manage
 * dependency injection for those classes as well as advice those classed with
 * Aspects and Caching. When the REST framework sees that Spring has already
 * created a bean for a needed class - that bean will be used instead of
 * instantiating a new object to be managed by the REST framework.
 * <p>
 * I have decided NOT to allow Spring to manage the REST filters. This is
 * because many of the filters require the REST framework to inject the current
 * request. Therfore the REST Framework creates singletons (beans) for each
 * filter and manages them internally. Spring knows nothing about them.
 * </p>
 * 
 * @author Abhinav Akey
 */
@Configuration
public class ApplicationSpringBeans {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationSpringBeans.class);

	// private static final String FIREBASE_RESOURCE = "billlive-2ebb8.json";
	// private static final String GOOGLE_SERVICE_ACCOUNT_RESOURCE =
	// "billlive-265d48bf3656.json";
	private static final String GOOGLE_SERVICE_ACCOUNT_APPLICATION_NAME = "billlive-webapp";
	private static final String FIREBASE_DATABASE_URL = "https://billlive-2ebb8.firebaseio.com";

	private static final byte[] GOOGLE_KMS_SALT = { (byte) 0x1E, (byte) 0x34, (byte) 0x16, (byte) 0x73, (byte) 0xC4,
			(byte) 0xB4, (byte) 0x47, (byte) 0xE2, (byte) 0x23, (byte) 0x66, (byte) 0x34, (byte) 0x67, (byte) 0x46,
			(byte) 0x12, (byte) 0x96, (byte) 0xD3 };

	private static final byte[] FIREBASE_AUTH_SALT = { (byte) 0xBA, (byte) 0x0, (byte) 0x76, (byte) 0x4E, (byte) 0x88,
			(byte) 0x7A, (byte) 0x12, (byte) 0x1D, (byte) 0x41, (byte) 0xEF, (byte) 0x89, (byte) 0x2E, (byte) 0x2E,
			(byte) 0xBF, (byte) 0x98, (byte) 0xFB };

	public static final String PROJECT_ID = "billlive-webapp";
	public static final String LOCATION = "asia-east1";
	public static final String RING_ID = "cookie-key";
	public static final String ENCRYPTION_KEY_ID = "BL-SC-Enc-Cookie-Key";
	public static final String HMAC_KEY_ID = "BL-SC-Hmac-Cookie-Key";
	public static final String VERSION = "1";
	
	private FirebaseApp firebaseApp;
	private CloudKMS cloudKMS;

	// private static final int ITERATION_COUNT = 1000;

	private static final int BYTES = 32;

	@Value("${googlekms.file:googlekms_key.enc}")
	private String googleKMSFile = "googlekms_key.enc";

	@Value("${keychain.file:billlive_key.enc}")
	private String keyChainFile = "billlive_key.enc";

	@Value("${googlekms.hash:aHZza2hyOGJuV0l5dXdyZTZiY3piSERLSkhzNzhiamhiYzd3aGpqY0t1OEpINnNudw==}")
	private String googleKMSHash = "aHZza2hyOGJuV0l5dXdyZTZiY3piSERLSkhzNzhiamhiYzd3aGpqY0t1OEpINnNudw==";

	@Value("${firebase.auth.file:firebase_auth_key.enc}")
	private String firebaseAuthFile = "firebase_auth_key.enc";

	@Value("${firebase.auth.hash:aGs4N0hLSmg3OGprSDg3WWhpdWhralRIaDg3SEpIUjZyaGtubVd4c3plNzZYTzc4Zm4=}")
	private String firebaseAuthHash = "aGs4N0hLSmg3OGprSDg3WWhpdWhralRIaDg3SEpIUjZyaGtubVd4c3plNzZYTzc4Zm4=";

	@Resource(name = "keyChain")
	private KeyChain keyChain;

	@Resource(name = "googleCloudKMS")
	private GoogleCloudKMS googleCloudKMS;

	// ******************************************************************//
	// Application-layer beans
	// ******************************************************************//

	@Bean
	public CloudKMS cloudKMS() {
		try {
			// Get the Decryption key from the hash
			byte[] decryptionKey = keyChain.generateDerivedKey(BYTES,
					Base64.decode(googleKMSHash.getBytes(Constants.CHAR_SET)), GOOGLE_KMS_SALT);

			// Load the googleKMS encrypted file.
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			URL resource = loader.getResource(googleKMSFile);
			// File billliveFile = new File(resource.toURI());
			// InputStream encryptedStream = new FileInputStream(billliveFile);

			StringBuilder contentBuilder = new StringBuilder();

			try (Stream<String> stream = Files.lines(Paths.get(resource.toURI()), StandardCharsets.UTF_8)) {
				stream.forEach(s -> contentBuilder.append(s).append("\n"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Decrypt the file
			SecretKeySpec encKey = new SecretKeySpec(decryptionKey, Constants.AES);
			EncryptionFactory.Encryption enc = EncryptionFactory.getInstance(encKey.getAlgorithm());
			byte[] encryptedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(contentBuilder.toString());
			byte[] googleKMSFileBytes = enc.decrypt(encKey.getEncoded(), encryptedBytes);
			// String googleKMSFileContent =
			// org.apache.commons.codec.binary.Base64
			// .encodeBase64URLSafeString(googleKMSFileBytes);
			String googleKMSFileContent = new String(googleKMSFileBytes, Constants.CHAR_SET);
			/*
			 * URL propertiesResource =
			 * loader.getResource("/properties/googlekms_key.enc"); File
			 * encodedFile = new File(propertiesResource.toURI());
			 * 
			 * FileWriter fw = new FileWriter(encodedFile);
			 * fw.write(googleKMSFileContent); fw.close();
			 */
			// Create the credential
			HttpTransport transport = new NetHttpTransport();
			JsonFactory jsonFactory = new JacksonFactory();
			GoogleCredential credential = GoogleCredential.fromStream(IOUtils.toInputStream(googleKMSFileContent))
					.createScoped(Collections.singleton(CloudKMSScopes.CLOUD_PLATFORM));

			// Depending on the environment that provides the default
			// credentials (e.g. Compute Engine, App
			// Engine), the credentials may require us to specify the scopes we
			// need explicitly.
			// Check for this case, and inject the scope if required.
			if (credential.createScopedRequired()) {
				credential = credential.createScoped(CloudKMSScopes.all());
			}

			CloudKMS cloudKMS = new CloudKMS.Builder(transport, jsonFactory, credential)
					.setApplicationName(GOOGLE_SERVICE_ACCOUNT_APPLICATION_NAME).build();
			this.setCloudKMS(cloudKMS);
			return cloudKMS;
		} catch (URISyntaxException | IOException | CryptoException e) {
			throw new BillliveServiceException(e.getMessage());
		}
	}

	@Bean
	public FirebaseApp generateFirebaseAuthServiceAccountDetails() {
		try {
			// Get the Decryption key from the hash
			byte[] decryptionKey = keyChain.generateDerivedKey(BYTES,
					Base64.decode(firebaseAuthHash.getBytes(Constants.CHAR_SET)), FIREBASE_AUTH_SALT);
			// Load the firebase auth encrypted file.
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			URL resource = loader.getResource(firebaseAuthFile);
			// File billliveFile = new File(resource.toURI());
			// InputStream encryptedStream = new FileInputStream(billliveFile);

			StringBuilder contentBuilder = new StringBuilder();

			try (Stream<String> stream = Files.lines(Paths.get(resource.toURI()), StandardCharsets.UTF_8)) {
				stream.forEach(s -> contentBuilder.append(s).append("\n"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Decrypt the file
			SecretKeySpec encKey = new SecretKeySpec(decryptionKey, Constants.AES);
			EncryptionFactory.Encryption enc = EncryptionFactory.getInstance(encKey.getAlgorithm());
			byte[] encryptedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(contentBuilder.toString());
			byte[] firebaeAuthFileBytes = enc.decrypt(encKey.getEncoded(), encryptedBytes);
			// String googleKMSFileContent =
			// org.apache.commons.codec.binary.Base64
			// .encodeBase64URLSafeString(firebaeAuthFileBytes);
			 String googleKMSFileContent = new String(firebaeAuthFileBytes,Constants.CHAR_SET);
			InputStream serviceAccount = new ByteArrayInputStream(googleKMSFileContent.getBytes());
			// Initialize the app with a service account, granting admin
			// privileges
			FirebaseOptions options;
			FirebaseApp baseApp = null;
			options = new FirebaseOptions.Builder().setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
					.setDatabaseUrl(FIREBASE_DATABASE_URL).build();
			List<FirebaseApp> apps = FirebaseApp.getApps();
			boolean isAppAlreadyInitialized = false;
			for (FirebaseApp app : apps) {
				if (Constants.BILLLIVE.equalsIgnoreCase(app.getName())) {
					isAppAlreadyInitialized = true;
					baseApp = app;
				}
			}
			if (!isAppAlreadyInitialized)
				baseApp = FirebaseApp.initializeApp(options, Constants.BILLLIVE);
			this.setFirebaseApp(baseApp);
			return baseApp;
		} catch (IOException | URISyntaxException | CryptoException e) {
			LOGGER.debug("Couldn't able to initialize app");
			throw new BillliveServiceException(e.getMessage());
		}

	}
	
	@Bean
	public KeyChainEntries keyChainEntries() {
		KeyChainEntries keyChainEntries = null;
		try {
			// ENC
			// Load the googleKMS encrypted file.
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			URL resource = loader.getResource(keyChainFile);
			// File billliveFile = new File(resource.toURI());
			// InputStream encryptedStream = new FileInputStream(billliveFile);
			StringBuilder contentBuilder = new StringBuilder();

			try (Stream<String> stream = Files.lines(Paths.get(resource.toURI()), StandardCharsets.UTF_8)) {
				stream.forEach(s -> contentBuilder.append(s).append("\n"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] encryptedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(contentBuilder.toString());

			byte[] decryptedPlainText = decrypt(encryptedBytes);
			//String googleKMSFileContent = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(decryptedPlainText); 
			InputStream plainTextStream = new ByteArrayInputStream(decryptedPlainText);
			keyChainEntries = keyChain.loadKeyChain(plainTextStream);
		} catch (URISyntaxException | IOException | CryptoException e) {
			throw new BillliveServiceException(e.getMessage());
		}
		return keyChainEntries;
	}

	@Bean
	public DatabaseReference databaseReference() {
		FirebaseApp baseApp = this.getFirebaseApp();
		if(baseApp == null){
			baseApp = generateFirebaseAuthServiceAccountDetails();
		}
		// As an admin, the app has access to read and write all data,
		// regardless of Security Rules
		DatabaseReference ref = FirebaseDatabase.getInstance(baseApp, FIREBASE_DATABASE_URL).getReference();
		// FirebaseDatabase.getInstance(baseApp,
		// "https://billlive-2ebb8.firebaseio.com").setPersistenceEnabled(true);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				Object document = dataSnapshot.getValue();
				System.out.println(document);
			}

			@Override
			public void onCancelled(DatabaseError error) {

			}
		});
		return ref;
	}

	@Bean
	public FirebaseAuth firebaseAuth() {
		FirebaseApp baseApp = this.getFirebaseApp();
		if(baseApp == null){
			baseApp = generateFirebaseAuthServiceAccountDetails();
		}
		// As an admin, the app has access to read and write all data,
		// regardless of Security Rules
		FirebaseAuth auth = FirebaseAuth.getInstance(baseApp);
		return auth;
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
		EncryptResponse response = this.getCloudKMS().projects().locations().keyRings().cryptoKeys()
				.encrypt(cryptoKeyName, request).execute();

		return response.decodeCiphertext();
	}
	
	/**
	 * Decrypts the given encrypted bytes, using the specified crypto key.
	 */
	public byte[] decrypt(byte[] encrypted) throws IOException {

		// The resource name of the cryptoKey
		String cryptoKeyName = String.format("projects/%s/locations/%s/keyRings/%s/cryptoKeys/%s", PROJECT_ID, LOCATION,
				RING_ID, ENCRYPTION_KEY_ID);

		DecryptRequest request = new DecryptRequest().encodeCiphertext(encrypted);
		DecryptResponse response = this.getCloudKMS().projects().locations().keyRings().cryptoKeys()
				.decrypt(cryptoKeyName, request).execute();

		return response.decodePlaintext();
	}

	public FirebaseApp getFirebaseApp() {
		return firebaseApp;
	}

	public void setFirebaseApp(FirebaseApp firebaseApp) {
		this.firebaseApp = firebaseApp;
	}

	public CloudKMS getCloudKMS() {
		return cloudKMS;
	}

	public void setCloudKMS(CloudKMS cloudKMS) {
		this.cloudKMS = cloudKMS;
	}
}
