package com.beatus.billlive.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.beatus.billlive.exception.BillliveServiceException;
import com.beatus.billlive.utils.Constants;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.cloudkms.v1.CloudKMS;
import com.google.api.services.cloudkms.v1.CloudKMSScopes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;;

/**
 * Root class for the application's Spring configuration. This class is
 * annotated with {@link ComponentScan} which will look for other classes in
 * this package with the {@link Configuration} annotation. It will not component
 * scan for any other Spring Stereotypes.
 *
 * @author Abhinav Akey
 * @since 1.0
 */
@Configuration
@ComponentScan(
        basePackages = "com.beatus.billlive",
        useDefaultFilters = false,
        includeFilters = {
            @ComponentScan.Filter(Configuration.class),
            @ComponentScan.Filter(Controller.class),
            @ComponentScan.Filter(Component.class),
        	@ComponentScan.Filter(Service.class)
        })
/*@PropertySource(
        name = "buildProperties", 
        value = "classpath:build.properties", 
        ignoreResourceNotFound = true)*/
@PropertySource(
        name = "applicationProperties",
        value = "classpath:application.properties", 
        ignoreResourceNotFound = false) 
public class ApplicationConfiguration {

    private static final Logger LOGGER 
            = LoggerFactory.getLogger(ApplicationConfiguration.class);
	private static final String FIREBASE_RESOURCE = "billlive-2ebb8.json";
	private static final String GOOGLE_SERVICE_ACCOUNT_RESOURCE = "billlive-265d48bf3656.json";
	private static final String GOOGLE_SERVICE_ACCOUNT_APPLICATION_NAME = "billlive";
	private static final String FIREBASE_DATABASE_URL = "https://billlive-2ebb8.firebaseio.com";
    // ******************************************************************//
    // Properities Configuration
    // ******************************************************************//

    @Bean
    @Profile("!test")
    public static PropertySourcesPlaceholderConfigurer pspc() {
                
        PropertySourcesPlaceholderConfigurer pspc
                = new PropertySourcesPlaceholderConfigurer();

        pspc.setLocalOverride(true);

        return pspc;
    }
    
    /**
     * Property Placeholder configuration for non-test profiles.
     * 
     * @return PropertySourcesPlaceholderConfigurer
     */
    @Bean
    @Profile("test")
    public static PropertySourcesPlaceholderConfigurer pspcTest() {

        PropertySourcesPlaceholderConfigurer pspc
                = new PropertySourcesPlaceholderConfigurer();

        pspc.setLocalOverride(true);

        return pspc;
    }
    
    // ******************************************************************//
    // Application-layer beans
    // ******************************************************************//
    
	@Bean
    public DatabaseReference databaseReference() {
    	FirebaseApp baseApp = null;
		try {
			// Fetch the service account key JSON file contents
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			URL resource = loader.getResource(FIREBASE_RESOURCE);
			File billliveFile = new File(resource.toURI());
			FileInputStream serviceAccount = new FileInputStream(billliveFile);
			
			// Initialize the app with a service account, granting admin privileges
			FirebaseOptions options;
				options = new FirebaseOptions.Builder()
				    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				    .setDatabaseUrl(FIREBASE_DATABASE_URL)
				    .build();
			List<FirebaseApp> apps = FirebaseApp.getApps();	
			boolean isAppAlreadyInitialized = false;
			for(FirebaseApp app : apps){
				if(Constants.BILLLIVE.equalsIgnoreCase(app.getName())){
					isAppAlreadyInitialized  = true;
					baseApp = app;
				}
			}
			if(!isAppAlreadyInitialized)
				baseApp = FirebaseApp.initializeApp(options, Constants.BILLLIVE);
	
		} catch (IOException | URISyntaxException e) {
			LOGGER.debug("Couldn't able to initialize app");
			e.printStackTrace();
		}

		// As an admin, the app has access to read and write all data, regardless of Security Rules
		DatabaseReference ref = FirebaseDatabase.getInstance(baseApp, FIREBASE_DATABASE_URL)
		    .getReference();
		//FirebaseDatabase.getInstance(baseApp, "https://billlive-2ebb8.firebaseio.com").setPersistenceEnabled(true);
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
	
	// ******************************************************************//
    // Application-layer beans
    // ******************************************************************//
    
	@Bean
    public static FirebaseAuth firebaseAuth() {
    	FirebaseApp baseApp = null;
		try {
			// Fetch the service account key JSON file contents
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			URL resource = loader.getResource(FIREBASE_RESOURCE);
			File billliveFile = new File(resource.toURI());
			FileInputStream serviceAccount = new FileInputStream(billliveFile);
			
			// Initialize the app with a service account, granting admin privileges
			FirebaseOptions options;
				options = new FirebaseOptions.Builder()
				    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				    .build();
			List<FirebaseApp> apps = FirebaseApp.getApps();	
			boolean isAppAlreadyInitialized = false;
			for(FirebaseApp app : apps){
				if(Constants.BILLLIVE.equalsIgnoreCase(app.getName())){
					isAppAlreadyInitialized  = true;
					baseApp = app;
				}
			}
			if(!isAppAlreadyInitialized)
				baseApp = FirebaseApp.initializeApp(options, Constants.BILLLIVE);
	
		} catch (IOException | URISyntaxException e) {
			LOGGER.debug("Couldn't able to initialize app");
			e.printStackTrace();
		}

		// As an admin, the app has access to read and write all data, regardless of Security Rules
		FirebaseAuth auth = FirebaseAuth.getInstance(baseApp);
		return auth;
    }
    
	@Bean
	public static CloudKMS cloudKMS() throws BillliveServiceException {
		try {
		    // Create the credential
		    HttpTransport transport = new NetHttpTransport();
		    JsonFactory jsonFactory = new JacksonFactory();
		    // Authorize the client using Application Default Credentials
		    // @see https://g.co/dv/identity/protocols/application-default-credentials
		    
		    ClassLoader loader = Thread.currentThread().getContextClassLoader();
			URL resource = loader.getResource(GOOGLE_SERVICE_ACCOUNT_RESOURCE);
			File billliveFile = new File(resource.toURI());
		    GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(billliveFile))
		    	    .createScoped(Collections.singleton(CloudKMSScopes.CLOUD_PLATFORM));
		    
		    // Depending on the environment that provides the default credentials (e.g. Compute Engine, App
		    // Engine), the credentials may require us to specify the scopes we need explicitly.
		    // Check for this case, and inject the scope if required.
		    if (credential.createScopedRequired()) {
		      credential = credential.createScoped(CloudKMSScopes.all());
		    }
		    
		    return new CloudKMS.Builder(transport, jsonFactory, credential)
		        .setApplicationName(GOOGLE_SERVICE_ACCOUNT_APPLICATION_NAME)
		        .build();
		} catch (URISyntaxException | IOException e) {
			throw new BillliveServiceException(e.getMessage());
		}
	  }
}
