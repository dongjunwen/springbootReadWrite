package com.dl.springboot.readwrite.service;

import com.dl.springboot.readwrite.config.ReadOnly;
import com.dl.springboot.readwrite.mapper.SysUserMapper;
import com.dl.springboot.readwrite.modal.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author:luiz
 * @Date: 2018/3/21 17:07
 * @Descripton:
 * @Modify :
 **/
@Service
public class UserService {
    private static final Logger logger= LoggerFactory.getLogger(UserService.class);
    @Resource
    private SysUserMapper sysUserMapper;

    @Transactional
    public void saveUser(SysUser sysUser){
        SysUser oldSysUser=sysUserMapper.selectByUserNo(sysUser.getUserNo());
        if(oldSysUser==null){
            sysUserMapper.insertSelective(sysUser);
        }else{
            sysUser.setId(oldSysUser.getId());
            sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
    }
    @ReadOnly
    public void saveUser1(SysUser sysUser){
        SysUser oldSysUser=sysUserMapper.selectByUserNo(sysUser.getUserNo());
        if(oldSysUser==null){
            sysUserMapper.insertSelective(sysUser);
        }else{
            sysUser.setId(oldSysUser.getId());
            sysUserMapper.updateByPrimaryKeySelective(sysUser);
        }
    }
    public SysUser findUserByNo(String userNo){
        logger.info("按照用户编号:{}开始查询....",userNo);
       return sysUserMapper.selectByUserNo(userNo);
    }

    @ReadOnly
    public SysUser getUserByNo(String userNo){
        logger.info("按照用户编号:{}开始查询....",userNo);
        return sysUserMapper.selectByUserNo(userNo);
    }
}
