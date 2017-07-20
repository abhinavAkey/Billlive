package com.beatus.billlive.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Configuration class that registers an event listener to reconfigure 
 * logging after the application starts. Note that this class is not used in 
 * the "test" profile.
 * 
 * @author Abhinav Akey
 */
@Configuration
@Profile("!test")
public class LoggingConfiguration {

    private static final Logger LOGGER 
            = LoggerFactory.getLogger(LoggingConfiguration.class);

    @Value("${com.beatus.billlive.env}")
    private String environment;

    /**
     * Stop logging to the console after the application starts. Logging will
     * go to the applog only.
     * 
     * @param event 
     * @throws ch.qos.logback.core.joran.spi.JoranException 
     */ 
    @EventListener 
    public void stopLoggingToConsole( 
            ContextRefreshedEvent event) throws JoranException { 
        
        if (environment.equals("local"))
            return;

        LOGGER.info("The application will now stop logging to the console."); 
        System.setProperty("com.beatus.billlive.logging", "applog"); 
        refreshLoggingConfig(); 
    }

    private void refreshLoggingConfig() throws JoranException {
        LoggerContext context 
                = (LoggerContext) LoggerFactory.getILoggerFactory();

        context.reset(); 
        JoranConfigurator configurator = new JoranConfigurator(); 
        configurator.setContext(context);
        
        configurator.doConfigure(
                this.getClass().getResourceAsStream("/logback.xml"));
    }
}
