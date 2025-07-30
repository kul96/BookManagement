package com.example.bookManagement.aspect;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

    private static final Logger logger = LoggerFactory.getLogger(Aspect.class);

    @Before("execution(* com.example.bookManagement.controller.Controller.*(..))")
    public void controllerLogger() {
        logger.info("Controller Layer starting");
    }

    @Before("execution(* com.example.bookManagement.service.Service.*(..))")
    public void serviceLogger() {
        logger.info("Service Layer starting");
    }

    @Before("execution(* com.example.bookManagement.util.MapperImpl.*(..))")
    public void mapperLogger() {
        logger.info("Mapper class starting");
    }
}
