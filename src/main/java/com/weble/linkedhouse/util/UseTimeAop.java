package com.weble.linkedhouse.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class UseTimeAop {

    @Around("execution(public * com.sparta.dockingfinalproject..*Controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        try {
            Object output = joinPoint.proceed();
            return output;
        } finally {
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;

            log.info("[[   APIUseTime   >>>   " + runTime + "   ]]   ");
        }
    }
}
