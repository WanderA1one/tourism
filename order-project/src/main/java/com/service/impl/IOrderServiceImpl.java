package com.service.impl;

import com.dao.IOrderDao;
import com.pojo.Customer;
import com.pojo.Orders;
import com.pojo.ResultList;
import com.service.IOrderService;
import com.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class IOrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderDao iOrderDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    TokenUtils tokenUtils;
    @Override
    public Integer getTotalOfOrders() {
        return iOrderDao.findTotalOfOrders();
    }

    @Override
    public ResultList findCustomerOrder(HttpServletRequest request) {
        Customer customerByToken = tokenUtils.findCustomerByToken(request);
        String customerId = customerByToken.getCustomerId();
        List values = redisTemplate.opsForHash().values(customerId);
        ResultList resultList = new ResultList();
        resultList.setList(values);
        return resultList;
    }

    @Override
    public ResultList findAll() {
        List<Orders> all = iOrderDao.findAll();
        ResultList resultList = new ResultList();
        resultList.setList(all);
        return resultList;
    }
}