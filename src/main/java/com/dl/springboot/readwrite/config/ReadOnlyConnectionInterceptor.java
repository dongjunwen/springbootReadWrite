package com.dl.springboot.readwrite.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Author:luiz
 * @Date: 2018/3/21 16:52
 * @Descripton:
 * @Modify :
 **/
@Aspect
@Component
public class ReadOnlyConnectionInterceptor implements Ordered {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReadOnlyConnectionInterceptor.class);

    @Around("@annotation(readOnly)")//在注解上加入切入点语法，实现方法
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, ReadOnly readOnly) throws Throwable {
        try{
            LOGGER.info("---------------set database connection  read only---------------");
            DataBaseContextHolder.setDataBaseType(DataBaseContextHolder.DataBaseType.SLAVE);
            Object result = proceedingJoinPoint.proceed();//让这个方法执行完毕
            return result;
        } finally {
            DataBaseContextHolder.clearDataBaseType();
            LOGGER.info("---------------clear database connection---------------");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
