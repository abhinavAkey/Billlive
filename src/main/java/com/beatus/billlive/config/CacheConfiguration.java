package com.beatus.billlive.config;

import javax.management.MBeanServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.management.ManagementService;

/**
 * Configuration for caching. Uses Spring annotations (not JSR-307 JCache 
 * annotations) and Ehcache as the cache provider. 
 * 
 * @author Abhinav Akey
 * @since 1.0
 */
@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

	@Value(value = "${item.cache.size:1000}")
    private String itemCacheSize;
    
    @Value(value = "${item.cache.time.to.idle:120}")
    private String itemCacheTimeToIdle;
    
    @Value(value = "${item.cache.time.to.live:120}")
    private String itemCacheTimeToLive;
    
    @Value(value = "${service.token.cache.size:1000}")
    private String serviceTokenCacheSize;
    
    @Value(value = "${service.token.cache.time.to.idle:120}")
    private String serviceTokenCacheTimeToIdle;
    
    @Value(value = "${service.token.cache.time.to.live:120}")
    private String serviceTokenCacheTimeToLive;
    
    @Value(value = "${max.elements.in.memory:10000}")
    private String maxElementsInMemory;
    
    @Value(value = "${max.elements.on.disk:0}")
    private String maxElementsOnDisk;
    
    @Value(value = "${memory.store.eviction.policy:LFU}")
    private String memoryStoreEvictionPolicy;

    /**
     * Ehcache-backed Spring {@link CacheManager}
     * 
     * @return CacheManager
     */
    @Bean
    @Override
    public EhCacheCacheManager cacheManager() {
        return new EhCacheCacheManager(
                ehCacheManagerFactoryBean()
                        .getObject());
    }

    /**
     * Ehcache CacheManger factory
     * 
     * @return EhCacheManagerFactoryBean
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        return new EhCacheManagerFactoryBean();
    }

    /**
     * Registers Cache operations and statistics with JMX.
     * 
     * @param cacheManager the EhCacheCacheManager
     * @param mBeanServer the MBeanServer
     * @return ManagementService
     */
    @Bean
    public ManagementService managementService(
            EhCacheCacheManager cacheManager, 
            MBeanServer mBeanServer) {

        return new ManagementService(
                cacheManager
                        .getCacheManager(), 
                mBeanServer, 
                true, 
                true, 
                true, 
                true, 
                true);
    }
    
    /**
     * Ehcache Cache factory
     * 
     * @return EhCacheFactoryBean
     */
    @SuppressWarnings("deprecation")
	@Bean
    public EhCacheFactoryBean ehCacheFactoryBean() {
    	EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
    	
    	ehCacheFactoryBean.setCacheManager(cacheManager().getCacheManager());
    	ehCacheFactoryBean.setMaxElementsInMemory(Integer.parseInt(maxElementsInMemory));
    	ehCacheFactoryBean.setMaxElementsOnDisk(Integer.parseInt(maxElementsOnDisk));
    	ehCacheFactoryBean.setEternal(false);
    	ehCacheFactoryBean.setOverflowToDisk(false);
        ehCacheFactoryBean.setMemoryStoreEvictionPolicy(memoryStoreEvictionPolicy);
    	return ehCacheFactoryBean;
    }
    
    /**
     * Ehcache item Cache factory
     * 
     * @return EhCacheFactoryBean
     */
	@SuppressWarnings("deprecation")
	@Bean
    public EhCacheFactoryBean itemCache() {
    	EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
    	
    	ehCacheFactoryBean.setCacheManager(cacheManager().getCacheManager());
    	ehCacheFactoryBean.setMaxElementsInMemory(Integer.parseInt(maxElementsInMemory));
    	ehCacheFactoryBean.setMaxElementsOnDisk(Integer.parseInt(maxElementsOnDisk));
    	ehCacheFactoryBean.setEternal(false);
    	ehCacheFactoryBean.setOverflowToDisk(false);
        ehCacheFactoryBean.setMemoryStoreEvictionPolicy(memoryStoreEvictionPolicy);
    	ehCacheFactoryBean.setTimeToIdle(Integer.parseInt(itemCacheTimeToIdle));
    	ehCacheFactoryBean.setTimeToLive(Integer.parseInt(itemCacheTimeToLive));
    	
    	return ehCacheFactoryBean;
    }

    
    /**
     * Ehcache Service Token Cache factory
     * 
     * @return EhCacheFactoryBean
     */
	@SuppressWarnings("deprecation")
	@Bean
    public EhCacheFactoryBean serviceTokenCache() {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
    	
    	ehCacheFactoryBean.setCacheManager(cacheManager().getCacheManager());
    	ehCacheFactoryBean.setMaxElementsInMemory(Integer.parseInt(maxElementsInMemory));
    	ehCacheFactoryBean.setMaxElementsOnDisk(Integer.parseInt(maxElementsOnDisk));
    	ehCacheFactoryBean.setEternal(false);
    	ehCacheFactoryBean.setOverflowToDisk(false);
        ehCacheFactoryBean.setMemoryStoreEvictionPolicy(memoryStoreEvictionPolicy);
    	ehCacheFactoryBean.setTimeToIdle(Integer.parseInt(serviceTokenCacheTimeToIdle));
    	ehCacheFactoryBean.setTimeToLive(Integer.parseInt(serviceTokenCacheTimeToLive));
    	
    	return ehCacheFactoryBean;
    }
}
