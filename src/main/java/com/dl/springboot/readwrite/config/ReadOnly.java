package com.dl.springboot.readwrite.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author:luiz
 * @Date: 2018/3/21 16:51
 * @Descripton:只读注解
 * @Modify :
 **/
@Target({ElementType.METHOD, ElementType.TYPE})//该注解应用在方法上
@Retention(RetentionPolicy.RUNTIME)//在运行时运行
public @interface ReadOnly {
}
