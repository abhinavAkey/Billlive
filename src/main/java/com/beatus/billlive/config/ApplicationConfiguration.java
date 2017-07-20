package com.beatus.billlive.config;

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

    
}
