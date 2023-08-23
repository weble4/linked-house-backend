package com.weble.linkedhouse.util;

import com.weble.linkedhouse.util.logtrace.LogTrace;
import com.weble.linkedhouse.util.logtrace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class UseTimeAop {

    private final LogTrace logTrace;

    public UseTimeAop(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(public * com.weble.linkedhouse..*Controller..*(..)) || " +
            "execution(public * com.weble.linkedhouse..*Service..*(..)) || " +
            "execution(public * com.weble.linkedhouse..*Repository..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;

        long startTime = System.currentTimeMillis();

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            Object result = joinPoint.proceed();

            logTrace.end(status);

            return result;
        }  catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
