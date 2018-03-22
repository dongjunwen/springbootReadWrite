package com.dl.springboot.readwrite.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:luiz
 * @Date: 2018/3/21 16:46
 * @Descripton:
 * @Modify :
 **/
public class DataBaseContextHolder {
    private static final Logger logger= LoggerFactory.getLogger(DataBaseContextHolder.class);
    //区分主从数据源
    public enum DataBaseType {
        MASTER, SLAVE
    }
    //线程局部变量
    private static final ThreadLocal<DataBaseType> contextHolder = new ThreadLocal<DataBaseType>();

    //往线程里边set数据类型
    public static void setDataBaseType(DataBaseType dataBaseType) {
        if(dataBaseType == null) throw new NullPointerException();
        logger.info("[DataBaseContextHolder]setDataBaseType:{}",dataBaseType);
        contextHolder.set(dataBaseType);
    }

    //从容器中获取数据类型
    public static DataBaseType getDataBaseType(){
        DataBaseType dataBaseType= contextHolder.get() == null ? DataBaseType.MASTER : contextHolder.get();
        logger.info("[DataBaseContextHolder]getDataBaseType:{}",dataBaseType);
        return dataBaseType;
    }
    //清空容器中的数据类型
    public static void clearDataBaseType(){
        contextHolder.remove();
    }

}
