package com.beatus.billlive.utils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

public class PoolingHttpClientFactory implements HttpClientFactory {

    private final int maxConns;
    private final int maxConnsPerRoute;
    private final int connectionTimeout;
    // private final int socketTimeout;
    // private final int revalidateMilliseconds;
    // private final boolean tcpNoDelay;
    // private final boolean keepAlive;
    // private final boolean reuseAddress;
 

    public PoolingHttpClientFactory( 
            int maxConns,
            int maxConnsPerRoute, 
            int connectionTimeout
            // int socketTimeout,
            // int revalidateMilliseconds,
            // boolean tcpNoDelay,
            // boolean keepAlive,
            // boolean reuseAddress
            ) {

        this.maxConns = maxConns;
        this.maxConnsPerRoute = maxConnsPerRoute;
        this.connectionTimeout = connectionTimeout;
        // this.socketTimeout = socketTimeout;
        // this.revalidateMilliseconds = revalidateMilliseconds;
        // this.tcpNoDelay = tcpNoDelay;
        // this.keepAlive = keepAlive;
        // this.reuseAddress = reuseAddress;
    }

    @Override
    public Client createClient() {

        // SocketConfig socketConfig = SocketConfig.custom()
        //         .setTcpNoDelay(tcpNoDelay)
        //         .setSoKeepAlive(keepAlive)
        //         .setSoReuseAddress(reuseAddress)
        //         .build();

        PoolingHttpClientConnectionManager connectionManager
                = new PoolingHttpClientConnectionManager();
        // connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setMaxTotal(maxConns);
        connectionManager.setDefaultMaxPerRoute(maxConnsPerRoute);
        // connectionManager.setValidateAfterInactivity(revalidateMilliseconds);

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.connectorProvider(new ApacheConnectorProvider());

        clientConfig.property(
                ApacheClientProperties.CONNECTION_MANAGER,
                connectionManager);

        clientConfig.property(
                ApacheClientProperties.REQUEST_CONFIG,
                RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                // .setSocketTimeout(socketTimeout)
                .build());

        return ClientBuilder.newClient(clientConfig);
    }
}
