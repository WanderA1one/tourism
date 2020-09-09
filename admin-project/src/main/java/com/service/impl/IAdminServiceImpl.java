package com.service.impl;

import com.dao.IAdminDao;
import com.pojo.Admin;
import com.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class IAdminServiceImpl implements IAdminService {
    @Autowired
    private IAdminDao iAdminDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public String adminLogin(Admin admin) {
        Admin adminByAdminPhone = iAdminDao.findAdminByAdminPhone(admin.getAdminPhone());
        if(adminByAdminPhone == null){
            return "没有该用户";
        }else if(!adminByAdminPhone.getAdminPassword().equals(admin.getAdminPassword())){
            return "密码错误";
        }
        UUID token = UUID.randomUUID();
        redisTemplate.opsForValue().set(token.toString(),adminByAdminPhone);
        redisTemplate.expire(token.toString(),30, TimeUnit.SECONDS);
        return token.toString();
    }
}
