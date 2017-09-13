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
 * Created by chandrashekar.v on 9/13/2017.
 */
@Aspect
public class ApplicationExceptionInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionInterceptor.class);

    @Pointcut("execution(public * com.techexplore.*.*(..)) ")
    public void applicationLevelInterceptor() {
    }

    @AfterThrowing("applicationLevelInterceptor()")
    public void interceptMiriSearchService(ProceedingJoinPoint pjp) throws AutoSuggestException {

        String interceptMessage = pjp.getTarget().getClass().getSimpleName() + " : " + pjp.getSignature().getName();
        LOG.error("Exception @ " + interceptMessage);

    }
}
