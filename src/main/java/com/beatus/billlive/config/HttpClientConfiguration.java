package com.beatus.billlive.config;

import javax.ws.rs.client.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beatus.billlive.utils.HttpClientFactory;
import com.beatus.billlive.utils.PoolingHttpClientFactory;

/**
 * Configuration class for the shared JSR-311 HTTP REST Client. Each 
 * adaptor/translator can add their own invocation concerns.
 * 
 * @author Abhinav Akey
 * @since 1.0
 */
@Configuration
public class HttpClientConfiguration {

    @Value("${httpClient.maxConnections:100}")
    private String maxConnections;

    @Value("${httpClient.maxConnectionsPerRoute:10}")
    private String maxConnectionsPerRoute;

    @Value("${httpClient.connectionTimeout:3000}")
    private String connectionTimeout;

    /**
     * Factory to make the shared HTTP Client. This implementation is backed by
     * the Apache HTTP Components library. Additional settings can be exposed 
     * if needed.
     *
     * @return {@link HttpClientFactory}
     * @see PoolingHttpClientFactory
     */
    @Bean
    public HttpClientFactory httpClientFactory() {
        return new PoolingHttpClientFactory(
        		Integer.parseInt(maxConnections),
        				Integer.parseInt(maxConnectionsPerRoute),
        						Integer.parseInt(connectionTimeout));
    }

    /**
     * The shared HTTP Client configured by the {@link HttpClientFactory}.
     *
     * @param httpClientFactory the {@link HttpClientFactory}
     * @return the shared {@link Client}
     */
    @Bean
    public Client httpClient(HttpClientFactory httpClientFactory) {
        return httpClientFactory.createClient();
    }
}
