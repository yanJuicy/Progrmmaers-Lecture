package org.prgms.kdt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(org.prgms.kdt.aop.TrackTime)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before method call {}", joinPoint.getSignature().toString());
        var startTime = System.nanoTime();
        var result = joinPoint.proceed();
        var endTime = System.nanoTime() - startTime;
        logger.info("After method call {} and time taken by {} nano secs", result, endTime);
        return result;
    }

}
