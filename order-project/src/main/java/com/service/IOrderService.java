package com.service;

import com.pojo.ResultList;

import javax.servlet.http.HttpServletRequest;

public interface IOrderService {

    Integer getTotalOfOrders();

    ResultList findCustomerOrder(HttpServletRequest request);

    ResultList findAll();
}
