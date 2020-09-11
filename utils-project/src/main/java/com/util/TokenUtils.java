package com.util;

import com.alibaba.fastjson.JSONObject;
import com.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class TokenUtils {
    @Autowired
    private static RedisTemplate redisTemplate;

    public static Customer findCustomerByToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = getToken(cookies);
        Object o = redisTemplate.opsForValue().get(token);
        //这里要将Object转为Admin类型，需要用到fastjson
        Object o1 = JSONObject.toJSON(o);
        Customer customer = JSONObject.parseObject(o1.toString(), Customer.class);
        return customer;
    }

    public static String getToken(Cookie[] cookies){
        if(cookies!=null){
            for (Cookie x:cookies
            ) {
                if(x.getName().equals("token")){
                    String token = x.getValue();
                    return token;
                }
            }
            return null;
        }
        return null;
    }
}
