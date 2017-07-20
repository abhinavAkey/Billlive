package com.beatus.billlive.config;

import org.springframework.context.annotation.Configuration;

/**
 * Spring-managed REST beans. This class creates Spring-managed beans for 
 * most of the classes that the REST framework needs. This allows Spring to
 * manage dependency injection for those classes as well as advice those 
 * classed with Aspects and Caching. When the REST framework sees that Spring
 * has already created a bean for a needed class - that bean will be used
 * instead of instantiating a new object to be managed by the REST framework.
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
public class RestSpringBeans  {

    /*@Bean
    public IdentityResource identityResource() {
        return new IdentityResource();
    }

    @Bean
    public SearchResource searchResource() {
        return new SearchResource();
    }

    @Bean
    public SessionResource sessionResource() {
        return new SessionResource();
    }

    @Bean
    public SecurityResource securityResource() {
        return new SecurityResource();
    }

    @Bean
    public TestingResource testingResource() {
        return new TestingResource();
    }

    @Bean
    public ValidationResource validationResource() {
        return new ValidationResource();
    }

    @Bean
    public VerizonResource verizonResource() {
        return new VerizonResource();
    }

    @Bean
    public ClientErrorExceptionHandler clientErrorExceptionHandler() { 
        return new ClientErrorExceptionHandler();
    }

    @Bean
    public ServerErrorExceptionHandler serverErrorExceptionHandler() { 
        return new ServerErrorExceptionHandler();
    }

    @Bean
    public RequestReaderExceptionHandler requestReaderExceptionHandler() { 
        return new RequestReaderExceptionHandler();
    }

    @Bean
    public ExcpetionHandlers.AccountNotRecoverableExceptionHandler
            accountNotRecoverableExceptionHandler() {
        return new ExcpetionHandlers.AccountNotRecoverableExceptionHandler();
    }

    @Bean
    public ExcpetionHandlers.NullSearchCriteriaExceptionHandler
            nullSearchCriteriaExceptionHandler() {
        return new ExcpetionHandlers.NullSearchCriteriaExceptionHandler();
    }

    @Bean(name = "insufficientScopeExceptionHandler")
    public InsufficientScopeExceptionHandler iseHandler() {
        return new InsufficientScopeExceptionHandler();
    }

    @Bean
    public RemoteCallExceptionHandler remoteCallExceptionHandler() {
        return new RemoteCallExceptionHandler();
    }

    @Bean
    public DomainExceptionHandler abstractDomainExceptionHandler() {
        return new DomainExceptionHandler();
    }

    @Bean
    public ThrowableHandler throwableHandler() {
        return new ThrowableHandler();
    }

    @Bean
    public ResponseWriter responseWriter() {
        return new ResponseWriter();
    }

    @Bean 
    public SecurityRegistry securityRegistry() {
        return new SecurityRegistry();
    }

    @Bean
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }

    @Value("${oauth2.authenticationServer.introspectionUrl:"
            + "${oauth2.authenticationServer}/oauth2/introspect}")
    private String oauth2AuthenticationServerIntrospectionUrl;

    @Bean
    @DependsOn({"httpClient"})
    public IntrospectionService introspectionService(
            Client httpClient)
            throws MalformedURLException {

        return new IntrospectionService(
                httpClient,
                new URL(oauth2AuthenticationServerIntrospectionUrl));
    }*/
}
