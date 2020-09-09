package com.service;

import com.pojo.Customer;
import com.pojo.ResultList;

import java.io.IOException;

public interface SearchCustomerService {

    ResultList<Customer> searchCustomer(String index,Integer page,Integer size) throws IOException;
}
