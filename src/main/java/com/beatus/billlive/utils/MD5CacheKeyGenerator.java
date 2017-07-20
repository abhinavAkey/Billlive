package com.beatus.billlive.utils;

import java.lang.reflect.Method;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.DigestUtils;

/**
 *
 * @author Abhinav Akey
 */
public class MD5CacheKeyGenerator implements KeyGenerator {

    private static final String INVALID_PARAMS
            = "MD5CacheKeyGenerator can only be used with "
            + "methods which have a single String parameter";

    @Override
    public Object generate(
            Object target, 
            Method method, 
            Object... params) {

        if (params.length != 1)
            throw new IllegalStateException(
                    INVALID_PARAMS);

        if (!(params[0] instanceof String))
            throw new IllegalStateException(
                    INVALID_PARAMS);

        return DigestUtils
                .md5DigestAsHex(
                        ((String) params[0])
                                .getBytes(UTF_8));
    }
}
