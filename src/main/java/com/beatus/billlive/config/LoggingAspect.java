package com.beatus.billlive.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect used to log method calls. By default, a dynamic logger is used to
 * log messages. This means that the class declaring the wrapped method is used
 * to create the logger. This allows the greatest granularity when turning on
 * logging. If dynamic logging is not desired, it can be disabled via the
 * constructor. This would force all logging though this class. The
 * {@link #logEnter(org.aspectj.lang.ProceedingJoinPoint)} and
 * {@link #logExit(org.aspectj.lang.ProceedingJoinPoint, boolean, double)}
 * methods can be overridden to provide custom log messaging.
 *
 * @author Abhinav Akey
 */ 
public class LoggingAspect {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LoggingAspect.class);

    private static final String ENTER_MESSAGE = "enter method {}";
    private static final String EXIT_MESSAGE_FORMAT = "exit method {} {} {}";
    private static final String SUCCESS_INDICATOR = "SUCCESS";
    private static final String FAILUER_INDICATOR = "FAILURE";
    private static final String TIME_FORMAT = "%.9f";

    private boolean useDynamicLogger;

    /**
     * Default constructor. Dynamic logging is not used.
     */
    public LoggingAspect() {
        this(false);
    }

    /**
     * Constructor allowing dynamic logging to be turned on. When dynamic
     * logging is enabled - the declaring class of the advised method will be
     * used to create the logger. This allows logging of messages on a
     * per-class basis.
     *
     * @param useDynamicLogger
     */
    public LoggingAspect(boolean useDynamicLogger) {
        this.useDynamicLogger = useDynamicLogger;
    }

    /**
     * logs the execution of the {@code ProceedingJoinPont} by logging
     * entrance and exit messages.
     *
     * @param pjp the {@code ProceedingJoinPoint}
     * @return the result of the {@code ProceedingJoinPoint}
     * @throws Throwable
     */
    
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        double durationSeconds;
        boolean didSucceed = false;

        logEnter(pjp);
        long start = System.nanoTime();

        try {
            result = pjp.proceed();
            didSucceed = true;

        } finally {
            durationSeconds = (System.nanoTime() - start) / 1e9d;
            logExit(pjp, didSucceed, durationSeconds);
        }

        return result;
    }

    /**
     * Logs the enter message. If dynamic logging is enabled the declaring class
     * will be used to construct the logger, otherwise this class will be used.
     * This method is {@code protected} so it can be overridden by sub-classes.
     *
     * @param pjp the {@code ProceedingJoinPoint}
     */
    protected void logEnter(ProceedingJoinPoint pjp) {
        Logger logger = getLogger(pjp);
        if (logger.isTraceEnabled())
            logger.trace(ENTER_MESSAGE, getMethodSignature(pjp));
    }

    /**
     * Gets the logger. If dynamic logging is enabled, the declaring class will
     * be used to construct the logger, otherwise this class's logger will be
     * used.
     *
     * @param pjp the {@code ProceedingJoinPoint}.
     * @return the logger
     */
    protected Logger getLogger(ProceedingJoinPoint pjp) {
        return useDynamicLogger()
                ? LoggerFactory.getLogger(getDeclaringClass(pjp)) : LOGGER;
    }

    /**
     * Gets the declaring class of a {@code ProceedingJoinPoint}.
     *
     * @param pjp the {@code ProceedingJoinPoint}
     * @return the declaring class
     */
    protected Class<?> getDeclaringClass(ProceedingJoinPoint pjp) {
        return pjp.getSignature().getDeclaringType();
    }

    /**
     * Gets the method signature from a {@code ProceedingJoinPoint}.
     *
     * @param pjp the {@code ProceedingJoinPoint}
     * @return the full method signature
     */
    protected String getMethodSignature(ProceedingJoinPoint pjp) {
        return pjp.getSignature().toShortString();
    }

    /**
     * Logs the exit message. If dynamic logging is enabled the declaring class
     * will be used to construct the logger, otherwise this class will be used.
     * This method is {@code protected} so it can be overridden by sub-classes.
     *
     * @param pjp the {@code ProceedingJoinPoint}
     * @param didSucceed boolean indicating success or failure
     * @param durationSeconds the duration in seconds
     */
    protected void logExit(
            ProceedingJoinPoint pjp,
            boolean didSucceed,
            double durationSeconds) {

        Logger logger = getLogger(pjp);

        if (logger.isTraceEnabled())
            logger.trace(EXIT_MESSAGE_FORMAT,
                    getMethodSignature(pjp),
                    didSucceed ? SUCCESS_INDICATOR : FAILUER_INDICATOR,
                    String.format(TIME_FORMAT, durationSeconds));
    }

    protected boolean useDynamicLogger() {
        return useDynamicLogger;
    }
}