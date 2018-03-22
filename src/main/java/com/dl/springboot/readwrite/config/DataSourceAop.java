package com.dl.springboot.readwrite.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author:luiz
 * @Date: 2018/3/21 18:04
 * @Descripton:
 * @Modify :
 **/
@Aspect
@Component
@Slf4j
@Order(-1)
public class DataSourceAop {

    @Before("execution(* com.dl.springboot.readwrite.service..*.find*(..))")
    public void setReadDataSourceType() {
        DataBaseContextHolder.setDataBaseType(DataBaseContextHolder.DataBaseType.SLAVE);
        log.info("dataSource切换到：Read");
    }

    @Before("execution(* com.dl.springboot.readwrite.*.mapper..*.insert*(..)) || execution(* com.dl.springboot.readwrite.*.mapper..*.update*(..))")
    public void setWriteDataSourceType() {
        DataBaseContextHolder.setDataBaseType(DataBaseContextHolder.DataBaseType.MASTER);
        log.info("dataSource切换到：write");
    }
}
