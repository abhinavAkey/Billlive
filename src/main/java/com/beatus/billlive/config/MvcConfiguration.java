package com.beatus.billlive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Configuration class for MVC. Sets up things like view resolvers, resource
 * mappings, and controllers. This class leverages Spring's 
 * {@link WebMvcConfigurerAdapter} which provides convenience methods for 
 * setting up Spring MVC.
 * 
 * @author Abhinav Akey
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    /*// this will be added to the build props bean exposed in all views
    private final static String CDN_BASE_KEY = "cdnBase";

    @Value("/statics/${pom.version}/css/*")
    private String verionedCssPath;

    @Value("/statics/${pom.version}/js/*")
    private String versionedJsPath;

    @Value("/statics/${pom.version}/img/*")
    private String verionedImgPath;

    @Value("${mvc.cdnBase:}")
    private String cdnBase;

    @Autowired
    Environment env;*/

    /**
     * Adds resource handlers to handle versioned static paths. This will 
     * allow the application server to be the Origin Server for statics and 
     * send "cache forever" cache directives as the paths will change on each
     * deployment.
     * 
     * @param registry the ResourceHandlerRegisty managed by the super class
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    	
        registry.addResourceHandler("billlive/resources/static/js/**")
		        .addResourceLocations("billlive/resources/static/js/");
		registry.addResourceHandler("billlive/resources/static/css/**")
		        .addResourceLocations("billlive/resources/static/css/");
		registry.addResourceHandler("billlive/resources/static/views/**")
		        .addResourceLocations("billlive/resources/static/views/");
		registry.addResourceHandler("billlive/resources/static/**")
		        .addResourceLocations("billlive/resources/static/");
		registry.addResourceHandler("billlive/webjars/**")
		        .addResourceLocations("billlive/webjars/");
    }

    /**
     * Properties bean that will be exposed in all views. Includes all build
     * properties (and selective others).
     * 
     * @return PropertiesFactoryBean that will create the properties bean
     *//*
    @Bean(name = "viewProperties")
    public PropertiesFactoryBean viewProperties() {
        PropertiesFactoryBean viewProperties = new PropertiesFactoryBean();
        viewProperties.setLocation(new ClassPathResource("build.properties"));

        Properties p = new Properties();

        p.setProperty(
                CDN_BASE_KEY, 
                cdnBase);

        p.setProperty(
                "spring.profile", 
                Arrays.toString(
                        env.getActiveProfiles()));

        viewProperties.setProperties(p);

        return viewProperties;
    }
*/
    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".html");
        return resolver;
    }


    /**
     * JSP View resolver.
     * 
     * @return internalResourceViewResolver
     */
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        /*InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".html");
        resolver.setCache(true);
        resolver.setExposedContextBeanNames("viewProperties");
        resolver.setExposeContextBeansAsAttributes(true);*/
    	
    	InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".html");
        return resolver;
    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setMaxUploadSize(268435456L);
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }
    
    
    /*@Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor());
    }*/
}
