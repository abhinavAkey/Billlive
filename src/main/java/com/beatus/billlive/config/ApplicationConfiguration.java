package com.beatus.billlive.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

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

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
			URL resource = loader.getResource("billlive-2ebb8.json");
			File billliveFile = new File(resource.toURI());
			FileInputStream serviceAccount = new FileInputStream(billliveFile);
			
			// Initialize the app with a service account, granting admin privileges
			FirebaseOptions options;
				options = new FirebaseOptions.Builder()
				    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				    .setDatabaseUrl("https://billlive-2ebb8.firebaseio.com")
				    .build();
			baseApp = FirebaseApp.initializeApp(options);
	
		} catch (IOException | URISyntaxException e) {
			LOGGER.debug("Couldn't able to initialize app");
			e.printStackTrace();
		}

		// As an admin, the app has access to read and write all data, regardless of Security Rules
		DatabaseReference ref = FirebaseDatabase.getInstance(baseApp, "https://billlive-2ebb8.firebaseio.com")
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
    
}
