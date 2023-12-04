package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

   // Logger logger = LoggerFactory.getLogger(LoggingAspect.class);  // instead we put @Slf4j annotation

    private String getUserName(){ //this method purpose is get the username from Keycloak
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount)authentication.getDetails();
        return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    public void anyProjectAndTaskControllerPC() {}

    @Before("anyProjectAndTaskControllerPC()")
    public void beforeAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint){
        log.info("Before -> Method: {} , User:{}"         //which method running and which user runs?
        ,joinPoint.getSignature().toShortString()
        ,getUserName());
    }

    @AfterReturning(pointcut = "anyProjectAndTaskControllerPC()" ,returning = "results")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Object results){
        log.info("After Returning -> Method: {} , User:{} , Results:{}"         //which method running and which user runs and what is result
                ,joinPoint.getSignature().toShortString()
                ,getUserName()
                ,results.toString());
    }

    @AfterThrowing(pointcut = "anyProjectAndTaskControllerPC()" ,throwing = "exception")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Exception exception){
        log.info("After Returning -> Method: {} , User:{} , Results:{}"         //which method running and which user runs and what is result
                ,joinPoint.getSignature().toShortString()
                ,getUserName()
                ,exception.getMessage());
    }
}
