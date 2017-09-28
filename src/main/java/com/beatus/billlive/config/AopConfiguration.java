package com.beatus.billlive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration class for AOP. This class contains static inner classes that
 * extend the common {@link LoggingAspect} with their own pointcuts. Beans 
 * are then created out of the classes which are managed by Spring. This allows
 * us to have a common way to do aspect logging which allows applications to 
 * add their own point cuts.
 * 
 * @author Abhinav Akey
 * @since 1.0
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopConfiguration {

     /**
     * Logging aspect for application service methods. Logs method entries at
     * DEBUG level, and method exits at INFO level with timing.
     *//*
    @Aspect
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class ApplicationServiceMethodsAspect extends LoggingAspect { 

        @Override
        @Around("execution(public * com.beatus.billlive"
                + ".application.service.*Service.*(..))")
        public Object log(ProceedingJoinPoint pjp) throws Throwable {
            return super.log(pjp);
        }
    }

    *//**
     * Logging aspect for domain service methods. Logs method entries at DEBUG
     * level, and method exits at INFO level with timing.
     *//*
    @Aspect
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class DomainServiceMethodsAspect extends LoggingAspect {

        @Override
        @Around("execution(public * com.beatus.billlive"
                + ".domain.model.*Service.*(..))")
        public Object log(ProceedingJoinPoint pjp) throws Throwable {
            return super.log(pjp);
        }
    }

    *//**
     * Logging aspect for repository methods. Logs method entries at DEBUG
     * level, and method exits at INFO level with timing.
     *//*
    @Aspect
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class InfrastructureMethodsAspect extends LoggingAspect {

        @Override
        @Around("execution(public * com.beatus.billlive"
                + ".infrastructure.**.*.*(..))")
        public Object log(ProceedingJoinPoint pjp) throws Throwable {
            return super.log(pjp);
        }
    }

    *//**
     * Logging aspect for REST methods. Logs method entries at DEBUG level, and
     * method exits at INFO level with timing.
     *//*
    @Aspect
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class RestMethodsAspect extends LoggingAspect {

        @Override
        @Around("execution(public * com.beatus.billlive"
                + ".portadaptor.rest..*.*(..))")
        public Object log(ProceedingJoinPoint pjp) throws Throwable {
            return super.log(pjp);
        }
    }

    @Bean
    public ApplicationServiceMethodsAspect applicationServiceMethodsAspect() {
        return new ApplicationServiceMethodsAspect();
    }

    @Bean
    public DomainServiceMethodsAspect domainServiceMethodsAspect() {
        return new DomainServiceMethodsAspect();
    }

    @Bean
    public InfrastructureMethodsAspect infrastructureMethodsAspect() {
        return new InfrastructureMethodsAspect();
    }

    @Bean
    public RestMethodsAspect restMethodsAspect() {
        return new RestMethodsAspect();
    }*/
}
