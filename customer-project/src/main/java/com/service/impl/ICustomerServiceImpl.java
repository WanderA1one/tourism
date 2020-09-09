package com.service.impl;

import com.dao.ICustomerDao;
import com.pojo.Customer;
import com.pojo.ResultList;
import com.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ICustomerServiceImpl implements ICustomerService {

    @Autowired
    ICustomerDao iCustomerDao;

    @Override
    public String customerLogin(Customer customer) {
        return null;
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

}
