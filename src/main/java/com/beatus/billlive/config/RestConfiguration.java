package com.beatus.billlive.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Jersey-specific configuration class that extends and wraps the JSR-311 
 * {@link Application} class. This is the entry point for the JSR-311
 * configuration.
 * 
 * @author Abhinav Akey
 */
@ApplicationPath("api")
public class RestConfiguration extends ResourceConfig {

    private static final String APPLICATION_NAME = "billlive";

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public RestConfiguration() {

        // Provider-specific properties
        property(ServerProperties.APPLICATION_NAME, APPLICATION_NAME); 
        property(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, true);

        // Providers
       /* this.register(ResponseWriter.class);

        // Resources
        this.register(SecurityResource.class);
        this.register(IdentityResource.class);
        this.register(WadlResource.class);

        // Exception Handlers
        this.register(RequestReaderExceptionHandler.class);
        this.register(DomainExceptionHandler.class);
        this.register(ThrowableHandler.class);
        this.register(ClientErrorExceptionHandler.class);
        this.register(ServerErrorExceptionHandler.class);*/
    }
}