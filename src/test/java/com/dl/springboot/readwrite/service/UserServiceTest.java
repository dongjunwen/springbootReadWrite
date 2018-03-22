package com.dl.springboot.readwrite.service;

import com.dl.springboot.readwrite.modal.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author:luiz
 * @Date: 2018/3/21 17:10
 * @Descripton:
 * @Modify :
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Resource
    UserService userService;
    @Test
    public void testSaveUser(){
        SysUser sysUser=new SysUser();
        sysUser.setUserNo("zhangsan");
        sysUser.setUserName("张三");
        userService.saveUser(sysUser);

    }

    @Test
    public void testSaveUser1(){
        SysUser sysUser=new SysUser();
        sysUser.setUserNo("zhangsan1");
        sysUser.setUserName("张三1");
        userService.saveUser1(sysUser);

    }

    @Test
    public void testFindUser(){
      SysUser sysUser =  userService.findUserByNo("zhangsan");
      if(sysUser!=null){
          System.out.println("用户信息:["+sysUser.toString()+"]");
      }else{
          System.err.println("用户不存在");
      }

    }

    @Test
    public void testGetUser(){
        SysUser sysUser =  userService.getUserByNo("zhangsan");
        if(sysUser!=null){
            System.out.println("用户信息:["+sysUser.toString()+"]");
        }else{
            System.err.println("用户不存在");
        }

    }
}
