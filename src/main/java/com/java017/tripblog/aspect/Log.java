package com.java017.tripblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author YuCheng
 * @date 2021/11/17 - 上午 01:25
 */

@Aspect
@Component
public class Log {

    private static final Logger log = LoggerFactory.getLogger(Log.class);

    @Pointcut("execution(* com.java017.tripblog.controller.*.*(..))")
    private void forControllerPackage() {

    }

    @Pointcut("execution(* com.java017.tripblog.service_impl.*.*(..))")
    private void forServicePackage() {

    }

    @Pointcut("execution(* com.java017.tripblog.repository.*.*(..))")
    private void forRepoPackage() {

    }

    @Pointcut("forControllerPackage() || forServicePackage() || forRepoPackage()")
    private void forAll() {

    }

    @Before("forAll()")
    private void before(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        StringBuilder argBuffer = new StringBuilder();
        int count = 0 ;

        for(Object arg : args) {
            if (arg != null) {
                argBuffer.append(arg);
                count ++ ;
                if (count < args.length) {
                    argBuffer.append(", ");
                }
            }
        }
		log.info("呼叫方法 : " + method + ", 參數 : (" + argBuffer + ")");
    }

    @AfterReturning(pointcut = "forAll()", returning = "result")
    private void afterReturning(JoinPoint joinPoint, Object result) {

        String method = joinPoint.getSignature().toShortString();
        log.info("方法返回 : " + method + ", 結果 : " + result);
    }

}
