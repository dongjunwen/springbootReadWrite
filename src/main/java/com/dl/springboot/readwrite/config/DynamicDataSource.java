package com.dl.springboot.readwrite.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author:luiz
 * @Date: 2018/3/21 16:48
 * @Descripton:
 * @Modify :
 **/
//mybatis动态代理类
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger logger= LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        DataBaseContextHolder.DataBaseType dataBaseType=DataBaseContextHolder.getDataBaseType();
        logger.info("[DynamicDataSource]determineCurrentLookupKey:{}",dataBaseType.name());
        return dataBaseType;
    }

}

