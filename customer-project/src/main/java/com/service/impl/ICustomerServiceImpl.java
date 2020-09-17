package com.service.impl;

import com.dao.ICustomerDao;
import com.pojo.Customer;
import com.pojo.CustomerReq;
import com.pojo.ResultList;
import com.service.ICustomerService;
import com.util.MD5;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ICustomerServiceImpl implements ICustomerService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ICustomerDao iCustomerDao;

    @Override
    public String customerLogin(Customer customer) {
        Customer byCustomerEmail = iCustomerDao.findByCustomerEmail(customer.getCustomerEmail());
        if (byCustomerEmail==null){
            return "没有该用户";
        }else {
            try {
                if(!MD5.validPassword(customer.getCustomerPassword(),byCustomerEmail.getCustomerPassword())){
                    return "密码错误";
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        UUID token = UUID.randomUUID();
        //以token为key ,查询出的用户对象为value存储到reids中
        redisTemplate.opsForValue().set(token.toString(),byCustomerEmail);
        redisTemplate.expire(token.toString(),30, TimeUnit.MINUTES);
        return token.toString();
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        return null;
    }

    @Override
    public ResultList findAllCustomers(Integer page,Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<Customer> all = iCustomerDao.findAll(pageRequest);
        List<Customer> content = all.getContent();
        long totalElements = all.getTotalElements();
        ResultList resultList = new ResultList();
        resultList.setList(content);
        resultList.setTotal(totalElements);
        return resultList;
    }

    @Override
    public Integer deleteByCustomerId(String customerId) {
        try{
            iCustomerDao.deleteById(customerId);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return iCustomerDao.saveAndFlush(customer);
    }

    @Override
    public ResultList findByNameOrCardId(String customerName,String customerCardId) {
        return iCustomerDao.findCustomersByCustomerNameOrCustomerCardIdLike(customerName,customerCardId);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return iCustomerDao.save(customer);
    }

    @Override
    public void deleteMoreCustomers(List<String> ids) {
        iCustomerDao.deleteMoreCustomers(ids);
    }

    @Override
    public Integer getTotals() {
        return iCustomerDao.getCountOfCustomers();
    }

    @Override
    public String sendMail(String s) {
        Customer byCustomerEmail = iCustomerDao.findByCustomerEmail(s);
        if(byCustomerEmail!=null){
            return "用户邮箱已被注册";
        }
        Map map = new HashMap<>();
        map.put("email",s);
        rabbitTemplate.convertAndSend("email-project",map);
        return "success";
    }

    @Override
    public String registry(CustomerReq customerReq) {
        String customerEmail = customerReq.getCustomerEmail();
        Object o = redisTemplate.opsForValue().get(customerEmail);
        if(o!=null){
            String customerCode = customerReq.getCustomerCode();
            if(o.toString().equals(customerCode)){
                Customer customer = new Customer();
                UUID uuid = UUID.randomUUID();
                customer.setCustomerId(""+uuid);
                customer.setCustomerRole(0);
                customer.setCustomerTime(new Date());
                try {
                    customerReq.setCustomerPassword(MD5.getEncryptedPwd(customerReq.getCustomerPassword()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                BeanUtils.copyProperties(customerReq,customer);
                System.out.println(customer.getCustomerPassword());
                iCustomerDao.save(customer);
                return "注册成功";
            }else{
                return "验证码错误";
            }
        }
        return "验证码超时";
    }

}
