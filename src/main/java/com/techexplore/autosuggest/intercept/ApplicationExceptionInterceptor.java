package com.techexplore.autosuggest.intercept;

import com.techexplore.autosuggest.framework.exception.AutoSuggestException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ApplicationExceptionInterceptor: Exception handling through AOP implementation that will be called post throwing any error during execution.
 * <p>
 * Created by chandrashekar.v on 9/13/2017.
 */
@Aspect
public class ApplicationExceptionInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionInterceptor.class);

    @Pointcut("execution(public * com.techexplore.*.*(..)) ")
    public void applicationLevelInterceptor() {
    }

    @AfterThrowing("applicationLevelInterceptor()")
    public void intercept(ProceedingJoinPoint pjp) throws AutoSuggestException {

        String interceptMessage = pjp.getTarget().getClass().getSimpleName() + " : " + pjp.getSignature().getName();
        LOG.error("Exception @ " + interceptMessage);

    }
}
