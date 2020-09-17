package com.service;

import com.pojo.Customer;
import com.pojo.CustomerReq;
import com.pojo.ResultList;

import java.util.List;

public interface ICustomerService {

    String customerLogin(Customer customer);

    Customer registerCustomer(Customer customer);

    ResultList findAllCustomers(Integer page,Integer size);

    Integer deleteByCustomerId(String customerId);

    Customer saveCustomer(Customer customer);

    ResultList findByNameOrCardId(String customerName,String customerCardId);

    Customer addCustomer(Customer customer);

    void deleteMoreCustomers(List<String> ids);

    Integer getTotals();

    String sendMail(String s);

    String registry(CustomerReq customerReq);
}
